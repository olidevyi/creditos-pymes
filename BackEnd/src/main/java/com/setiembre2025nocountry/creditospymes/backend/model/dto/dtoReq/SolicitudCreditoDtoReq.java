package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;

public record SolicitudCreditoDtoReq(
        Integer montoSolicitado,
        String plazo,
        String proposito,
        EstadoSolicitud estadoSolicitud
) {}
