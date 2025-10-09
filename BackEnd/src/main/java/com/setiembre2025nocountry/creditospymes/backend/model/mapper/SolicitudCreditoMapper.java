package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;

public class SolicitudCreditoMapper {
    public SolicitudCreditoDtoRes toDto(SolicitudCredito solicitudCredito){
        return SolicitudCreditoDtoRes.builder()
                .estadoSolicitud(solicitudCredito.getEstadoSolicitud())
                .fechaActualizacion(solicitudCredito.getFechaActualizacion())
                .fechaCreacion(solicitudCredito.getFechaCreacion())
                .montoSolicitado(solicitudCredito.getMontoSolicitado())
                .proposito(solicitudCredito.getProposito())
                .build();
    }

    public SolicitudCredito toSolicitudCredito(SolicitudCreditoDtoReq solicitudCreditoDtoReq){
        return SolicitudCredito.builder()
                .proposito(solicitudCreditoDtoReq.proposito())
                .fechaCreacion(solicitudCreditoDtoReq.fechaCreacion())
                .fechaActualizacion(solicitudCreditoDtoReq.fechaActualizacion())
                .estadoSolicitud(solicitudCreditoDtoReq.estadoSolicitud())
                .montoSolicitado(solicitudCreditoDtoReq.montoSolicitado())
                .build();
    }
}