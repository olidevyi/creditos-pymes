package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SolicitudCreditoDtoReq(
        @NotNull(message = "Monto solicitado requerido!") @Positive BigDecimal montoSolicitado,
        @NotBlank(message = "Plazo requerido!") String plazo,
        @NotBlank(message = "Proposito requerido!") String proposito,
        EstadoSolicitud estadoSolicitud,
        @NotNull(message = "Id de solicitante requerido!") Long solicitanteId,
        @NotNull(message = "Id de empresa requerido!") Long empresaId,
        LocalDate fechaEstimadaDesembolso,
        String comentariosAdicionales,
        LocalDateTime fechaEnvio
) {}
