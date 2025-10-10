package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import org.springframework.stereotype.Component;

@Component
public class SolicitudCreditoMapper {
    public SolicitudCreditoDtoRes toDto(SolicitudCredito s){
        return SolicitudCreditoDtoRes.builder()
                .id(s.getId())
                .montoSolicitado(s.getMontoSolicitado())
                .plazo(s.getPlazo())
                .proposito(s.getProposito())
                .estadoSolicitud(s.getEstadoSolicitud())
                .fechaCreacion(s.getFechaCreacion())
                .fechaActualizacion(s.getFechaActualizacion())
                .build();
    }
    public SolicitudCredito toEntity(SolicitudCreditoDtoReq r){
        return SolicitudCredito.builder()
                .montoSolicitado(r.montoSolicitado())
                .plazo(r.plazo())
                .proposito(r.proposito())
                .estadoSolicitud(r.estadoSolicitud())
                .build();
    }
}
