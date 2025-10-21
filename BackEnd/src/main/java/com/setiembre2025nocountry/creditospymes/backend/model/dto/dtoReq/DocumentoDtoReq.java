package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.TipoDocumento;
import jakarta.validation.constraints.NotNull;

public record DocumentoDtoReq(
        @NotNull TipoDocumento tipoDocumento,
        EstadoDocumento estadoDocumento,
        String observaciones,
        @NotNull Long solicitudId,
        @NotNull Long cargadoPorId
) {
}
