package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UsuarioDtoRes( String nombre,
                             String email,
                             String passWord,
                             LocalDate fechaRegistro) {
}