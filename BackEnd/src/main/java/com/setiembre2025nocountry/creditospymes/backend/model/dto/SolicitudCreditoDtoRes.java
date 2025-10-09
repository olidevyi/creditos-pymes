package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record SolicitudCreditoDtoRes( Integer montoSolicitado,
                                      String proposito,
                                      EstadoSolicitud estadoSolicitud,
                                      LocalTime fechaCreacion,
                                      LocalTime fechaActualizacion) {
}