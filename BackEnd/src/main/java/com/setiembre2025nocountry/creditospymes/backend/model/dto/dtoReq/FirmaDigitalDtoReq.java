package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FirmaDigitalDtoReq(
        @NotNull(message = "Id de solicitud requerido!") Long solicitudId,
        @NotNull(message = "Id firmante requerido!") Long firmanteId,
        @NotBlank(message = "Hash firma requerido!") String hashFirma,
        EstadoFirmaDigital estado,
        String ipFirmante,
        String documentoFirmadoUrl
) {}
