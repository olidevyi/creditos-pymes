package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FirmaDigitalDtoReq(
        @NotNull Long solicitudId,
        @NotNull Long firmanteId,
        @NotBlank String hashFirma,
        EstadoFirmaDigital estado,
        String ipFirmante,
        String documentoFirmadoUrl
) {}
