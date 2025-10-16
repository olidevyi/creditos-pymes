package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolicitudCreditoDtoReq(
        @NotNull @Positive BigDecimal montoSolicitado,
        @NotBlank String plazo,
        @NotBlank String proposito,
        EstadoSolicitud estadoSolicitud,
        @NotNull Long solicitanteId,
        @NotNull Long empresaId,
        LocalDate fechaEstimadaDesembolso,
        String comentariosAdicionales,
        LocalDateTime fechaEnvio
) {}
