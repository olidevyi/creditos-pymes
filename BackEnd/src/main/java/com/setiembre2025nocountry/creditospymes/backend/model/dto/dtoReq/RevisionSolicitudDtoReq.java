package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.ResultadoRevision;
import jakarta.validation.constraints.NotNull;

public record RevisionSolicitudDtoReq(
        @NotNull(message = "Id de solicitud requerido!") Long solicitudId,
        @NotNull(message = "Id de administrador requerido!") Long administradorId,
        Long documentoId,
        @NotNull(message = "Resultado de revision requerido!") ResultadoRevision resultado,
        String comentarios
) {}
