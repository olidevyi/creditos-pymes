package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UsuarioDtoReq(
        @NotBlank(message = "Nombre requerido!") String nombre,
        @NotBlank(message = "Apellido requerido!") String apellido,
        @NotNull(message = "Fecha de nacimiento requerido!") LocalDate fechaNacimiento,
        @NotBlank(message = "Correo requerido!") @Email String email,
        @NotBlank(message = "Telefono requerido!") String telefono,
        @NotBlank(message = "Documento de identidad requerido!") String documentoIdentidad,
        @NotBlank(message = "Contraseña requerida!") String password,
        Rol rol,
        Boolean activo
) {}
