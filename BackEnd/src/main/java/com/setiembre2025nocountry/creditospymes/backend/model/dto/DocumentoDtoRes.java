package com.setiembre2025nocountry.creditospymes.backend.model.dto;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.TipoDocumento;

import java.time.LocalDateTime;

public record DocumentoDtoRes(
        Long id,
        String nombreOriginal,
        String rutaAlmacenamiento,
        TipoDocumento tipoDocumento,
        EstadoDocumento estadoDocumento,
        String observaciones,
        LocalDateTime fechaCarga,
        Long solicitudId,
        Long cargadoPorId,
        String cargadoPorNombre
) {}
