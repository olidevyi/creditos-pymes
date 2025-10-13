package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmpresaDtoRes(
        Long id,
        String nombre,
        String razonSocial,
        String cuit,
        String email,
        String direccion,
        String telefono,
        String sector,
        Integer anioFundacion,
        Integer numeroEmpleados,
        BigDecimal facturacionAnualPromedio,
        String sitioWeb,
        String descripcion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        Long administradorId,
        String administradorNombre
) {}
