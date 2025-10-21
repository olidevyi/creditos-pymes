package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.FirmaDigital;
import org.springframework.stereotype.Component;

@Component
public class FirmaDigitalMapper {

    public FirmaDigitalDtoRes toDto(FirmaDigital f) {
        Long solicitudId = f.getSolicitudCredito() != null ? f.getSolicitudCredito().getId() : null;
        Long firmanteId = f.getFirmante() != null ? f.getFirmante().getId() : null;
        String firmanteNombre = f.getFirmante() != null
                ? (f.getFirmante().getNombre() + " " + f.getFirmante().getApellido()).trim()
                : null;

        return new FirmaDigitalDtoRes(
                f.getId(),
                f.getDocumento() != null ? f.getDocumento().getId() : null,
                solicitudId,
                firmanteId,
                firmanteNombre,
                f.getEstado().name(),
                f.getSignatureFormat(),
                f.getDigestAlg(),
                f.getSignAlg(),
                f.getPolicyOid(),
                f.getDocSha256(),
                f.getSignatureStorageKey(),
                f.getTsaTokenStorageKey(),
                f.getCertSubject(),
                f.getCertIssuer(),
                f.getCertSerial(),
                f.getCertFingerprint(),
                f.getIpFirmante(),
                f.getUserAgent(),
                f.getFechaFirma()
        );
    }
}
