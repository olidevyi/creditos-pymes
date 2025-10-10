package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoFirma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "FirmaDigital")
public class FirmaDigital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proveedor")
    private String proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoFirma estado;

    @Column(name = "documentoFirmadoUrl")
    private String documentoFirmadoUrl;

    @Column(name = "fechaFirma")
    private LocalDateTime fechaFirma;
}
