package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoRevision;

import java.time.LocalDateTime;

public record RevisionDtoRes(
        Long id,
        Long solicitudId,
        Long administradorId,
        String administradorNombre,
        Long documentoId,
        EstadoRevision estado,
        String comentarios,
        LocalDateTime fechaRevision
) {}
