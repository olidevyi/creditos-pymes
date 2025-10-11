package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirma;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FirmaDigitalDtoReq(
        String proveedor,
        EstadoFirma estado,
        String documentoFirmadoUrl,
        LocalDateTime fechaFirma
) {
}
