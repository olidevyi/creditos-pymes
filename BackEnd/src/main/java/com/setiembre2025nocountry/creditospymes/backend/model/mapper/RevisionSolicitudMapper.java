package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionSolicitudDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.RevisionSolicitud;
import org.springframework.stereotype.Component;

@Component
public class RevisionSolicitudMapper {

    public RevisionSolicitudDtoRes toDto(RevisionSolicitud revisionSolicitud) {
        Long solicitudId = revisionSolicitud.getSolicitudCredito() != null
                ? revisionSolicitud.getSolicitudCredito().getId()
                : null;
        Long administradorId = revisionSolicitud.getAdministrador() != null
                ? revisionSolicitud.getAdministrador().getId()
                : null;
        String administradorNombre = revisionSolicitud.getAdministrador() != null
                ? revisionSolicitud.getAdministrador().getNombre() + " " + revisionSolicitud.getAdministrador().getApellido()
                : null;
        Long documentoId = revisionSolicitud.getDocumentoRevisado() != null
                ? revisionSolicitud.getDocumentoRevisado().getId()
                : null;

        return new RevisionSolicitudDtoRes(
                revisionSolicitud.getId(),
                solicitudId,
                administradorId,
                administradorNombre,
                documentoId,
                revisionSolicitud.getResultado(),
                revisionSolicitud.getComentarios(),
                revisionSolicitud.getFechaRevision()
        );
    }

    public RevisionSolicitud toEntity(RevisionSolicitudDtoReq request) {
        RevisionSolicitud revision = new RevisionSolicitud();
        revision.setResultado(request.resultado());
        revision.setComentarios(request.comentarios());
        return revision;
    }
}
