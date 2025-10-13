package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoRevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"solicitudCredito", "administrador", "documento"})
@Table(name = "revision")
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_credito_id")
    private SolicitudCredito solicitudCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id")
    private Usuario administrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id")
    private Documento documento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EstadoRevision estado;

    @Column(length = 1000)
    private String comentarios;

    @CreationTimestamp
    private LocalDateTime fechaRevision;
}
