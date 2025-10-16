package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String apellido;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, unique = true, length = 30)
    private String documentoIdentidad;

    @Column(nullable = false, length = 120)
    private String password;

    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    private LocalDateTime ultimoAcceso;

    @Column(nullable = false)
    @Builder.Default
    private boolean activo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Rol rol;

    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<SolicitudCredito> solicitudes = new HashSet<>();

    @OneToMany(mappedBy = "cargadoPor", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Documento> documentos = new HashSet<>();

    @OneToMany(mappedBy = "firmante", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<FirmaDigital> firmasDigitales = new HashSet<>();

    @OneToMany(mappedBy = "administrador", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RevisionSolicitud> revisiones = new HashSet<>();
}