package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioDtoRes(
        Long id,
        String nombre,
        String apellido,
        LocalDate fechaNacimiento,
        String email,
        String telefono,
        String documentoIdentidad,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion,
        LocalDateTime ultimoAcceso,
        boolean activo,
        Rol rol
) {}
