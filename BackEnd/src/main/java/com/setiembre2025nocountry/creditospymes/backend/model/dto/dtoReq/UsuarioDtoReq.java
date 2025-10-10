package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDtoReq(
        @NotBlank String nombre,
        @NotBlank String email,
        @NotBlank String passWord,
        Rol rol
) {}
