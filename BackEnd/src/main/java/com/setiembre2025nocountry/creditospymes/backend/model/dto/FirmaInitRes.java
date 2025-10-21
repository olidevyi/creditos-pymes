package com.setiembre2025nocountry.creditospymes.backend.model.dto;

public record FirmaInitRes(
        Long firmaId,
        String docSha256,
        String digestAlg,   // ej: SHA-256
        String toSignHash,  // hash canónico a firmar
        String policyOid,
        String signatureFormat // PAdES/CAdES
) {}