package com.setiembre2025nocountry.creditospymes.backend.model.dto;

public record FirmaDigitalDtoRes(
        Long id,
        Long documentoId,
        Long solicitudId,
        Long firmanteId,
        String firmanteNombre,
        String estado,
        String signatureFormat,
        String digestAlg,
        String signAlg,
        String policyOid,
        String docSha256,
        String signatureStorageKey,
        String tsaTokenStorageKey,
        String certSubject,
        String certIssuer,
        String certSerial,
        String certFingerprint,
        String ipFirmante,
        String userAgent,
        java.time.LocalDateTime fechaFirma
) {}
