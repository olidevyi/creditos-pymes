package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

public record FirmaInitReq(
        Long documentoId,
        Long firmanteId,
        Long solicitudId,   // opcional: fallback si el Documento no tiene solicitud asociada
        String motivo,      // opcional PAdES
        String ubicacion    // opcional PAdES
) {}
