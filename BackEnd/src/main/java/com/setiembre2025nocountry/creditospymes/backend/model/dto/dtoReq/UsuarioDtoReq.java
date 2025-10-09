package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UsuarioDtoReq(String nombre,
                            String passWord,
                            @NotBlank String email,
                            LocalDate fechaRegistro) {
}