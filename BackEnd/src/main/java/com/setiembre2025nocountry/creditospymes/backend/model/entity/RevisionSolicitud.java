package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.ResultadoRevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "revision_solicitud")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitud_id")
    private SolicitudCredito solicitudCredito;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrador_id")
    private Usuario administrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id")
    private Documento documentoRevisado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ResultadoRevision resultado;

    @Column(length = 500)
    private String comentarios;

    @CreationTimestamp
    private LocalDateTime fechaRevision;
}
