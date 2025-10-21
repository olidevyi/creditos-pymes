package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.DocumentoMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.DocumentoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.DocumentoServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import com.setiembre2025nocountry.creditospymes.backend.storage.StorageService;
import com.setiembre2025nocountry.creditospymes.backend.storage.StoredObject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class DocumentoServisImpl implements DocumentoServis {

    private final DocumentoRepository documentoRepository;
    private final SolicitudCreditoRepository solicitudCreditoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DocumentoMapper documentoMapper;
    private final StorageService storageService;

    @Value("${app.files.max-size-bytes:52428800}") // 50 MB por defecto
    private long maxBytes;

    private static final Set<String> MIME_ALLOWED = Set.of(
            "application/pdf",
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    public DocumentoServisImpl(DocumentoRepository documentoRepository,
                               SolicitudCreditoRepository solicitudCreditoRepository,
                               UsuarioRepository usuarioRepository,
                               DocumentoMapper documentoMapper,
                               StorageService storageService) {
        this.documentoRepository = documentoRepository;
        this.solicitudCreditoRepository = solicitudCreditoRepository;
        this.usuarioRepository = usuarioRepository;
        this.documentoMapper = documentoMapper;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public DocumentoDtoRes upload(MultipartFile file, DocumentoDtoReq meta) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Archivo vacío");
        String ct = safeContentType(file);
        long len = file.getSize();
        enforceLimits(ct, len);

        StoredObject so;
        try (var in = file.getInputStream()) {
            so = storageService.store(in, len, ct, file.getOriginalFilename());
        }

        SolicitudCredito solicitud = solicitudCreditoRepository.findById(meta.solicitudId())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada: " + meta.solicitudId()));
        Usuario usuario = usuarioRepository.findById(meta.cargadoPorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + meta.cargadoPorId()));

        Documento doc = new Documento();
        documentoMapper.applyMeta(doc, meta);
        documentoMapper.fillFileAndRefs(
                doc,
                file.getOriginalFilename(),
                ct,
                so.length(),
                so.key(),
                so.sha256(),
                solicitud,
                usuario
        );

        documentoRepository.save(doc);
        return documentoMapper.toDto(doc);
    }

    @Transactional
    public DocumentoDtoRes createDocumento(DocumentoDtoReq meta) {
        var solicitud = solicitudCreditoRepository.findById(meta.solicitudId())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada: " + meta.solicitudId()));
        var usuario = usuarioRepository.findById(meta.cargadoPorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + meta.cargadoPorId()));

        Documento doc = new Documento();
        documentoMapper.applyMeta(doc, meta);        // solo metadatos de negocio
        doc.setSolicitudCredito(solicitud);
        doc.setCargadoPor(usuario);

        // Inicializar campos de archivo “vacío”
        doc.setNombreOriginal(null);
        doc.setTipoContenido(null);
        doc.setTamano(0L);           // usa null si tu entidad lo permite
        doc.setStorageKey(null);
        doc.setSha256(null);

        documentoRepository.save(doc);
        return documentoMapper.toDto(doc);
    }

    @Override
    public DocumentoDtoRes getDocumentoById(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + id));
        return documentoMapper.toDto(documento);
    }

    @Override
    public List<DocumentoDtoRes> getAllDocumentos() {
        return documentoRepository.findAll().stream()
                .map(documentoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public DocumentoDtoRes updateDocumentoMeta(Long id, DocumentoDtoReq meta) {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + id));

        documentoMapper.applyMeta(doc, meta);

        if (!doc.getSolicitudCredito().getId().equals(meta.solicitudId())) {
            SolicitudCredito nuevaSol = solicitudCreditoRepository.findById(meta.solicitudId())
                    .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada: " + meta.solicitudId()));
            doc.setSolicitudCredito(nuevaSol);
        }
        if (!doc.getCargadoPor().getId().equals(meta.cargadoPorId())) {
            Usuario nuevoUsuario = usuarioRepository.findById(meta.cargadoPorId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + meta.cargadoPorId()));
            doc.setCargadoPor(nuevoUsuario);
        }

        documentoRepository.save(doc);
        return documentoMapper.toDto(doc);
    }

    @Override
    @Transactional
    public DocumentoDtoRes replaceFile(Long id, MultipartFile file) throws IOException {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + id));

        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Archivo vacío");
        String ct = safeContentType(file);
        long len = file.getSize();
        enforceLimits(ct, len);

        // Borrar el anterior si existe
        storageService.delete(doc.getStorageKey());

        StoredObject so;
        try (var in = file.getInputStream()) {
            so = storageService.store(in, len, ct, file.getOriginalFilename());
        }

        // Actualizar metadatos de archivo
        doc.setNombreOriginal(file.getOriginalFilename());
        doc.setTipoContenido(ct);
        doc.setTamano(so.length());
        doc.setStorageKey(so.key());
        doc.setSha256(so.sha256());

        documentoRepository.save(doc);
        return documentoMapper.toDto(doc);
    }

    @Override
    @Transactional
    public void deleteDocumento(Long id) {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + id));
        // Intentar eliminar el objeto del storage. Si falla, dejar rastro en logs pero continuar con la transacción si aplica tu política.
        try {
            storageService.delete(doc.getStorageKey());
        } catch (IOException ex) {
            // log.warn("No se pudo eliminar del storage: " + doc.getStorageKey(), ex);
        }
        documentoRepository.delete(doc);
    }

    @Override
    public InputStreamWithMeta downloadStream(Long id) throws IOException {
        Documento doc = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado: " + id));
        return storageService.load(doc.getStorageKey());
    }

    private String safeContentType(MultipartFile file) {
        String ct = file.getContentType();
        return (ct == null || ct.isBlank()) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : ct;
    }

    private void enforceLimits(String contentType, long length) {
        if (length <= 0) throw new IllegalArgumentException("Tamaño inválido");
        if (length > maxBytes) throw new IllegalArgumentException("Archivo excede el máximo permitido");
        if (!MIME_ALLOWED.contains(contentType)) throw new IllegalArgumentException("Tipo de archivo no permitido");
    }
}
