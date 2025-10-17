package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EmpresaDtoReq(
        @NotBlank(message = "Nombre requerido!") String nombre,
        @NotBlank(message = "Razon social requerida!") String razonSocial,
        @NotBlank(message = "CUIT requerido!") String cuit,
        @NotBlank(message = "Correo requerido!") @Email String email,
        @NotBlank(message = "Direccion requerida!") String direccion,
        @NotBlank(message = "Telefono requerido!") String telefono,
        String sector,
        @PositiveOrZero Integer anioFundacion,
        @PositiveOrZero Integer numeroEmpleados,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal facturacionAnualPromedio,
        String sitioWeb,
        String descripcion,
        @NotNull(message = "Id de administrador requerido!") Long administradorId
) {}
