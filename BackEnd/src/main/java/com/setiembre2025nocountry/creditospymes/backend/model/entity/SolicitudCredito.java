package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud_credito")
public class SolicitudCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montoSolicitado;

    @Column(nullable = false, length = 40)
    private String plazo;

    @Column(nullable = false, length = 200)
    private String proposito;

    private LocalDate fechaEstimadaDesembolso;

    @Column(length = 500)
    private String comentariosAdicionales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private EstadoSolicitud estadoSolicitud = EstadoSolicitud.BORRADOR;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @Column
    private LocalDateTime fechaEnvio;

    @OneToMany(mappedBy = "solicitudCredito", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Documento> documentos = new HashSet<>();

    @OneToMany(mappedBy = "solicitudCredito", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RevisionSolicitud> revisiones = new HashSet<>();

    @OneToMany(mappedBy = "solicitudCredito", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<FirmaDigital> firmasDigitales = new HashSet<>();
}
