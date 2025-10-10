package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoResultado;
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
@Table(name = "KycRequest")
public class KycRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proveedor")
    private String proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado")
    private EstadoResultado resultado;

    @Column(name = "respuestaRaw")
    private String respuestaRaw;

    @Column(name = "fechaVerificacion")
    private LocalDateTime fechaVerificacion;
}
