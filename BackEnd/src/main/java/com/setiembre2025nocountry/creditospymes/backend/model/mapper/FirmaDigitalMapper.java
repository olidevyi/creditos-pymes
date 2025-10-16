package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.FirmaDigital;
import org.springframework.stereotype.Component;

@Component
public class FirmaDigitalMapper {

    public FirmaDigitalDtoRes toDto(FirmaDigital firmaDigital) {
        Long solicitudId = firmaDigital.getSolicitudCredito() != null ? firmaDigital.getSolicitudCredito().getId() : null;
        Long firmanteId = firmaDigital.getFirmante() != null ? firmaDigital.getFirmante().getId() : null;
        String firmanteNombre = firmaDigital.getFirmante() != null
                ? firmaDigital.getFirmante().getNombre() + " " + firmaDigital.getFirmante().getApellido()
                : null;

        return new FirmaDigitalDtoRes(
                firmaDigital.getId(),
                solicitudId,
                firmanteId,
                firmanteNombre,
                firmaDigital.getEstado(),
                firmaDigital.getFechaFirma(),
                firmaDigital.getHashFirma(),
                firmaDigital.getIpFirmante(),
                firmaDigital.getDocumentoFirmadoUrl()
        );
    }

    public FirmaDigital toEntity(FirmaDigitalDtoReq request) {
        FirmaDigital firmaDigital = new FirmaDigital();
        firmaDigital.setEstado(request.estado() != null ? request.estado() : EstadoFirmaDigital.PENDIENTE);
        firmaDigital.setHashFirma(request.hashFirma());
        firmaDigital.setIpFirmante(request.ipFirmante());
        firmaDigital.setDocumentoFirmadoUrl(request.documentoFirmadoUrl());
        return firmaDigital;
    }
}
