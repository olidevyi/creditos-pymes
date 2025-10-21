package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

public record FirmaCompleteReq(
        Long firmaId,
        String signatureFormat,
        String signAlg,              // NUEVO: "RSA" | "ECDSA"
        byte[] signatureContainer,   // CMS o PDF firmado (base64 en JSON)
        String certSubject,
        String certIssuer,
        String certSerial,
        String certFingerprint
) {}