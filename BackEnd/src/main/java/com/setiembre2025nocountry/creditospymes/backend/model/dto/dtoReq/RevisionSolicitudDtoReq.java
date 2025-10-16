package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.ResultadoRevision;
import jakarta.validation.constraints.NotNull;

public record RevisionSolicitudDtoReq(
        @NotNull Long solicitudId,
        @NotNull Long administradorId,
        Long documentoId,
        @NotNull ResultadoRevision resultado,
        String comentarios
) {}
