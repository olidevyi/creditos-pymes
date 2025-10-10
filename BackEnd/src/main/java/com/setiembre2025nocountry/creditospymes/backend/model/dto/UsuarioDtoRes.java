package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record UsuarioDtoRes(
        Long id,
        String nombre,
        String email,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion,
        Rol rol
) {}
