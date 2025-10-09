package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import lombok.Builder;

@Builder

public record EmpresaDtoRes( String razonSocial,
                             String cuit,
                             String direccion,
                             String telefono) {
}