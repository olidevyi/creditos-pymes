package com.setiembre2025nocountry.creditospymes.backend.model.entity;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud_credito")
public class SolicitudCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Integer montoSolicitado;
    private String Plazo;
    private String proposito;
    private EstadoSolicitud estadoSolicitud;
    private LocalTime fechaCreacion;
    private LocalTime fechaActualizacion;
}