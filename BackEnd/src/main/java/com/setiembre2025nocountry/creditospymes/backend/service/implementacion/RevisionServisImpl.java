package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Revision;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.RevisionMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.DocumentoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.RevisionRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RevisionServisImpl implements RevisionServis {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private RevisionMapper revisionMapper;

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public RevisionDtoRes createRevision(RevisionDtoReq request) {
        Revision revision = revisionMapper.toEntity(request);
        revision.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        revision.setAdministrador(obtenerUsuario(request.administradorId()));
        revision.setDocumento(obtenerDocumentoOpcional(request.documentoId()));
        Revision saved = revisionRepository.save(revision);
        return revisionMapper.toDto(saved);
    }

    @Override
    public RevisionDtoRes getRevisionById(Long id) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión no encontrada con id: " + id));
        return revisionMapper.toDto(revision);
    }

    @Override
    public RevisionDtoRes updateRevision(Long id, RevisionDtoReq request) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión no encontrada con id: " + id));

        revision.setEstado(request.estado());
        revision.setComentarios(request.comentarios());

        if (revision.getSolicitudCredito() == null
                || !Objects.equals(revision.getSolicitudCredito().getId(), request.solicitudId())) {
            revision.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        }

        if (revision.getAdministrador() == null
                || !Objects.equals(revision.getAdministrador().getId(), request.administradorId())) {
            revision.setAdministrador(obtenerUsuario(request.administradorId()));
        }

        Long documentoId = request.documentoId();
        if (documentoId != null) {
            if (revision.getDocumento() == null
                    || !Objects.equals(revision.getDocumento().getId(), documentoId)) {
                revision.setDocumento(obtenerDocumento(documentoId));
            }
        } else {
            revision.setDocumento(null);
        }

        Revision actualizado = revisionRepository.save(revision);
        return revisionMapper.toDto(actualizado);
    }

    @Override
    public void deleteRevision(Long id) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión no encontrada con id: " + id));
        revisionRepository.delete(revision);
    }

    @Override
    public List<RevisionDtoRes> getAllRevisiones() {
        return revisionRepository.findAll()
                .stream()
                .map(revisionMapper::toDto)
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

    private Documento obtenerDocumento(Long documentoId) {
        return documentoRepository.findById(documentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el documento con id " + documentoId));
    }

    private Documento obtenerDocumentoOpcional(Long documentoId) {
        if (documentoId == null) {
            return null;
        }
        return obtenerDocumento(documentoId);
    }
}
