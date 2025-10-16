package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Revision;
import org.springframework.stereotype.Component;

@Component
public class RevisionMapper {

    public RevisionDtoRes toDto(Revision revision) {
        Long solicitudId = revision.getSolicitudCredito() != null
                ? revision.getSolicitudCredito().getId()
                : null;
        Long administradorId = revision.getAdministrador() != null
                ? revision.getAdministrador().getId()
                : null;
        String administradorNombre = revision.getAdministrador() != null
                ? revision.getAdministrador().getNombre() + " " + revision.getAdministrador().getApellido()
                : null;
        Long documentoId = revision.getDocumento() != null
                ? revision.getDocumento().getId()
                : null;

        return new RevisionDtoRes(
                revision.getId(),
                solicitudId,
                administradorId,
                administradorNombre,
                documentoId,
                revision.getEstado(),
                revision.getComentarios(),
                revision.getFechaRevision()
        );
    }

    public Revision toEntity(RevisionDtoReq request) {
        Revision revision = new Revision();
        revision.setEstado(request.estado());
        revision.setComentarios(request.comentarios());
        return revision;
    }
}
