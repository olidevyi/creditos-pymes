package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.TipoDocumento;
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
@ToString(exclude = {"solicitudCredito", "cargadoPor"})
@Table(name = "documento",
        indexes = {
                @Index(name = "idx_documento_storage_key", columnList = "storage_key", unique = true),
                @Index(name = "idx_documento_sha256", columnList = "sha256")
        })
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Metadatos del archivo
    @Column(nullable = false, length = 160)
    private String nombreOriginal;

    @Column(nullable = false, length = 120)
    private String tipoContenido;

    @Column(nullable = false)
    private Long tamano;

    // Puntero en storage externo
    @Column(name = "storage_key", nullable = false, length = 200, unique = true)
    private String storageKey;

    // Integridad y deduplicación
    @Column(nullable = false, length = 64)
    private String sha256;

    // Atributos de negocio
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EstadoDocumento estadoDocumento;

    @Column(length = 500)
    private String observaciones;

    @CreationTimestamp
    private LocalDateTime fechaCarga;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_credito_id", nullable = false)
    private SolicitudCredito solicitudCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_carga_id", nullable = false)
    private Usuario cargadoPor;
}
