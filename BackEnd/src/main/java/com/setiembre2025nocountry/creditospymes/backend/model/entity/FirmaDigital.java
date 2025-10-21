package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"solicitudCredito", "firmante", "documento"})
@Table(name = "firma_digital",
        indexes = {
                @Index(name = "idx_firma_doc", columnList = "documento_id"),
                @Index(name = "idx_firma_sha_doc", columnList = "doc_sha256"),
                @Index(name = "idx_firma_estado", columnList = "estado")
        })
public class FirmaDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Documento firmado (versión exacta)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    // Para traza fuerte aunque el doc cambie luego
    @Column(name = "doc_sha256", nullable = false, length = 64)
    private String docSha256;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_credito_id", nullable = false)
    private SolicitudCredito solicitudCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firmante_id", nullable = false)
    private Usuario firmante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EstadoFirmaDigital estado; // PENDIENTE, EN_PROCESO, FIRMADA, INVALIDA, REVOCADA

    // Contenedor/artefactos en storage (no en DB)
    @Column(name = "signature_storage_key", length = 200)
    private String signatureStorageKey; // CMS/PAdES result

    @Column(name = "tsa_token_storage_key", length = 200)
    private String tsaTokenStorageKey; // RFC3161 opcional si no es PAdES-LTV inline

    // Algoritmos y formato
    @Column(name = "signature_format", length = 20)   // PAdES | CAdES | XAdES
    private String signatureFormat;

    @Column(name = "digest_alg", length = 20)         // SHA-256, SHA-512
    private String digestAlg;

    @Column(name = "sign_alg", length = 40)           // RSA, ECDSA
    private String signAlg;

    @Column(name = "policy_oid", length = 80)
    private String policyOid; // Política de firma si aplica

    // Certificados
    @Column(name = "cert_subject", length = 256)
    private String certSubject;

    @Column(name = "cert_issuer", length = 256)
    private String certIssuer;

    @Column(name = "cert_serial", length = 64)
    private String certSerial;

    @Column(name = "cert_fingerprint", length = 64)
    private String certFingerprint; // SHA-256 del cert

    // Metadatos operativos
    @Column(length = 45)
    private String ipFirmante;

    @Column(length = 255)
    private String userAgent;

    @Column(length = 100)
    private String providerTxnId; // id de transacción en proveedor externo si aplica

    @CreationTimestamp
    private LocalDateTime fechaFirma;
}
