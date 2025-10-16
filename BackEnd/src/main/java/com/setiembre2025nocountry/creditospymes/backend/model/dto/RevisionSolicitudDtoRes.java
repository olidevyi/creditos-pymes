package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.ResultadoRevision;

import java.time.LocalDateTime;

public record RevisionSolicitudDtoRes(
        Long id,
        Long solicitudId,
        Long administradorId,
        String administradorNombre,
        Long documentoId,
        ResultadoRevision resultado,
        String comentarios,
        LocalDateTime fechaRevision
) {}
