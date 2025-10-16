package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EmpresaDtoReq(
        @NotBlank String nombre,
        @NotBlank String razonSocial,
        @NotBlank String cuit,
        @NotBlank @Email String email,
        @NotBlank String direccion,
        @NotBlank String telefono,
        String sector,
        @PositiveOrZero Integer anioFundacion,
        @PositiveOrZero Integer numeroEmpleados,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal facturacionAnualPromedio,
        String sitioWeb,
        String descripcion,
        Long administradorId
) {}
