package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String razonSocial;

    @Column(nullable = false, unique = true, length = 60)
    private String cuit;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 150)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(length = 80)
    private String sector;

    private Integer anioFundacion;

    private Integer numeroEmpleados;

    @Column(precision = 15, scale = 2)
    private BigDecimal facturacionAnualPromedio;

    @Column(length = 150)
    private String sitioWeb;

    @Column(length = 500)
    private String descripcion;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id")
    private Usuario administradorResponsable;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<SolicitudCredito> solicitudes = new HashSet<>();
}