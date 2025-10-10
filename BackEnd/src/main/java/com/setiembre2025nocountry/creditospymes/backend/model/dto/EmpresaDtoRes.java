package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import lombok.Builder;

@Builder
public record EmpresaDtoRes(
        Long id,
        String nombre,
        String razonSocial,
        String email,
        String cuit,
        String direccion,
        String telefono
) {}
