package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirma;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FirmaDigitalDtoRes(
        Long id,
        String proveedor,
        EstadoFirma estado,
        String documentoFirmadoUrl,
        LocalDateTime fechaFirma
) {
}
