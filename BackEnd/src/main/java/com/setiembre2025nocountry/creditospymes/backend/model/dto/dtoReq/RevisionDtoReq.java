package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoRevision;
import jakarta.validation.constraints.NotNull;

public record RevisionDtoReq(
        @NotNull Long solicitudId,
        @NotNull Long administradorId,
        Long documentoId,
        @NotNull EstadoRevision estado,
        String comentarios
) {}
