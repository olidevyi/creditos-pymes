package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record SolicitudCreditoDtoRes(
        Long id,
        Integer montoSolicitado,
        String plazo,
        String proposito,
        EstadoSolicitud estadoSolicitud,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
