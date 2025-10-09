package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;

import java.time.LocalTime;

public record SolicitudCreditoDtoReq(Integer montoSolicitado,
                                     String proposito,
                                     String plazo,
                                     EstadoSolicitud estadoSolicitud,
                                     LocalTime fechaCreacion,
                                     LocalTime fechaActualizacion) {
}
