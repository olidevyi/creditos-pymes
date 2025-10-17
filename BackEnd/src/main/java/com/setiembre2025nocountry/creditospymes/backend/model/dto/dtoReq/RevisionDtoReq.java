package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoRevision;
import jakarta.validation.constraints.NotNull;

public record RevisionDtoReq(
        @NotNull(message = "Id de solicitud requerido!") Long solicitudId,
        @NotNull(message = "Id de administrador requerido!") Long administradorId,
        Long documentoId,
        @NotNull(message = "Estado de revision requerido!") EstadoRevision estado,
        String comentarios
) {}
