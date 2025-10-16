package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record SolicitudCreditoDtoRes(
        Long id,
        BigDecimal montoSolicitado,
        String plazo,
        String proposito,
        EstadoSolicitud estadoSolicitud,
        Long solicitanteId,
        String solicitanteNombre,
        Long empresaId,
        String empresaNombre,
        LocalDate fechaEstimadaDesembolso,
        String comentariosAdicionales,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        LocalDateTime fechaEnvio,
        Set<DocumentoDtoRes> documentos,
        Set<RevisionSolicitudDtoRes> revisiones,
        Set<FirmaDigitalDtoRes> firmasDigitales
) {}
