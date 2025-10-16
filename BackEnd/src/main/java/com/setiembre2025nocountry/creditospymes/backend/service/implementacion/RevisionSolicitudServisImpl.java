package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionSolicitudDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.RevisionSolicitud;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.RevisionSolicitudMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.DocumentoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.RevisionSolicitudRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionSolicitudServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RevisionSolicitudServisImpl implements RevisionSolicitudServis {

    @Autowired
    private RevisionSolicitudRepository revisionSolicitudRepository;

    @Autowired
    private RevisionSolicitudMapper revisionSolicitudMapper;

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public RevisionSolicitudDtoRes createRevisionSolicitud(RevisionSolicitudDtoReq request) {
        RevisionSolicitud revision = revisionSolicitudMapper.toEntity(request);
        revision.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        revision.setAdministrador(obtenerUsuario(request.administradorId()));
        revision.setDocumentoRevisado(obtenerDocumentoOpcional(request.documentoId()));
        RevisionSolicitud saved = revisionSolicitudRepository.save(revision);
        return revisionSolicitudMapper.toDto(saved);
    }

    @Override
    public RevisionSolicitudDtoRes getRevisionSolicitudById(Long id) {
        RevisionSolicitud revision = revisionSolicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión de solicitud no encontrada con id: " + id));
        return revisionSolicitudMapper.toDto(revision);
    }

    @Override
    public RevisionSolicitudDtoRes updateRevisionSolicitud(Long id, RevisionSolicitudDtoReq request) {
        RevisionSolicitud revision = revisionSolicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión de solicitud no encontrada con id: " + id));

        revision.setResultado(request.resultado());
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
            if (revision.getDocumentoRevisado() == null
                    || !Objects.equals(revision.getDocumentoRevisado().getId(), documentoId)) {
                revision.setDocumentoRevisado(obtenerDocumento(documentoId));
            }
        } else {
            revision.setDocumentoRevisado(null);
        }

        RevisionSolicitud actualizado = revisionSolicitudRepository.save(revision);
        return revisionSolicitudMapper.toDto(actualizado);
    }

    @Override
    public void deleteRevisionSolicitud(Long id) {
        RevisionSolicitud revision = revisionSolicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revisión de solicitud no encontrada con id: " + id));
        revisionSolicitudRepository.delete(revision);
    }

    @Override
    public List<RevisionSolicitudDtoRes> getAllRevisionesSolicitud() {
        return revisionSolicitudRepository.findAll()
                .stream()
                .map(revisionSolicitudMapper::toDto)
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
