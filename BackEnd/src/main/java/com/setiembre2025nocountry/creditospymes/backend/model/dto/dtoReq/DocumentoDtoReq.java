package com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq;

import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DocumentoDtoReq(
        @NotBlank(message = "Nombre requerido!") String nombreOriginal,
        @NotBlank(message = "Ruta de almacenamiento requerida!") String rutaAlmacenamiento,
        @NotNull(message = "Tipo de documento requerido!") TipoDocumento tipoDocumento,
        @NotNull(message = "Estado de documento requerido!") EstadoDocumento estadoDocumento,
        String observaciones,
        @NotNull(message = "Id de Solicitud requerido!") Long solicitudId,
        @NotNull(message = "Id de carga requerido!") Long cargadoPorId
) {}
