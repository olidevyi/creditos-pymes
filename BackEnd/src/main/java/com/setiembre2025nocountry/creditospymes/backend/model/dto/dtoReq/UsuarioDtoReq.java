package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UsuarioDtoReq(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotNull LocalDate fechaNacimiento,
        @NotBlank @Email String email,
        @NotBlank String telefono,
        @NotBlank String documentoIdentidad,
        @NotBlank String password,
        Rol rol,
        Boolean activo
) {}
