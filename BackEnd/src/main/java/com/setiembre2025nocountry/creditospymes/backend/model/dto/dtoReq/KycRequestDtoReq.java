package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoResultado;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record KycRequestDtoReq(
        String proveedor,
        EstadoResultado resultado,
        String respuestaRaw,
        LocalDateTime fechaVerificacion
) {
}
