package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirmaDigital;
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
@ToString(exclude = {"solicitudCredito", "firmante"})
@Table(name = "firma_digital")
public class FirmaDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_credito_id")
    private SolicitudCredito solicitudCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firmante_id")
    private Usuario firmante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EstadoFirmaDigital estado;

    @Column(nullable = false, length = 512)
    private String hashFirma;

    @Column(length = 45)
    private String ipFirmante;

    @Column(length = 255)
    private String documentoFirmadoUrl;

    @CreationTimestamp
    private LocalDateTime fechaFirma;
}
