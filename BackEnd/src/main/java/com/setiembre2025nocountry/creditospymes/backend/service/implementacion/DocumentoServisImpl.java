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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DocumentoServisImpl implements DocumentoServis {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public DocumentoDtoRes createDocumento(DocumentoDtoReq request) {
        Documento documento = documentoMapper.toEntity(request);
        documento.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        documento.setCargadoPor(obtenerUsuario(request.cargadoPorId()));
        Documento saved = documentoRepository.save(documento);
        return documentoMapper.toDto(saved);
    }

    @Override
    public DocumentoDtoRes getDocumentoById(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con id: " + id));
        return documentoMapper.toDto(documento);
    }

    @Override
    public DocumentoDtoRes updateDocumento(Long id, DocumentoDtoReq request) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con id: " + id));

        documento.setNombreOriginal(request.nombreOriginal());
        documento.setRutaAlmacenamiento(request.rutaAlmacenamiento());
        documento.setTipoDocumento(request.tipoDocumento());
        if (request.estadoDocumento() != null) {
            documento.setEstadoDocumento(request.estadoDocumento());
        }
        documento.setObservaciones(request.observaciones());

        if (documento.getSolicitudCredito() == null
                || !Objects.equals(documento.getSolicitudCredito().getId(), request.solicitudId())) {
            documento.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        }

        if (documento.getCargadoPor() == null
                || !Objects.equals(documento.getCargadoPor().getId(), request.cargadoPorId())) {
            documento.setCargadoPor(obtenerUsuario(request.cargadoPorId()));
        }

        Documento actualizado = documentoRepository.save(documento);
        return documentoMapper.toDto(actualizado);
    }

    @Override
    public void deleteDocumento(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con id: " + id));
        documentoRepository.delete(documento);
    }

    @Override
    public List<DocumentoDtoRes> getAllDocumentos() {
        return documentoRepository.findAll()
                .stream()
                .map(documentoMapper::toDto)
                .collect(Collectors.toList());
    }

    private SolicitudCredito obtenerSolicitud(Long solicitudId) {
        return solicitudCreditoRepository.findById(solicitudId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la solicitud con id " + solicitudId));
    }

    private Usuario obtenerUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id " + usuarioId));
    }
}
