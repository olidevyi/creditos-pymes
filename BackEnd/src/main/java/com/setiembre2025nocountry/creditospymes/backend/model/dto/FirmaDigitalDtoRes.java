package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;

import java.time.LocalDateTime;

public record FirmaDigitalDtoRes(
        Long id,
        Long solicitudId,
        Long firmanteId,
        String firmanteNombre,
        EstadoFirmaDigital estado,
        LocalDateTime fechaFirma,
        String hashFirma,
        String ipFirmante,
        String documentoFirmadoUrl
) {}
