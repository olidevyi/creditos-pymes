package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoDtoRes toDto(Documento documento) {
        Long solicitanteId = documento.getSolicitudCredito() != null ? documento.getSolicitudCredito().getId() : null;
        Long cargadoPorId = documento.getCargadoPor() != null ? documento.getCargadoPor().getId() : null;
        String cargadoPorNombre = documento.getCargadoPor() != null
                ? documento.getCargadoPor().getNombre() + " " + documento.getCargadoPor().getApellido()
                : null;

        return new DocumentoDtoRes(
                documento.getId(),
                documento.getNombreOriginal(),
                documento.getRutaAlmacenamiento(),
                documento.getTipoDocumento(),
                documento.getEstadoDocumento(),
                documento.getObservaciones(),
                documento.getFechaCarga(),
                solicitanteId,
                cargadoPorId,
                cargadoPorNombre
        );
    }

    public Documento toEntity(DocumentoDtoReq request) {
        Documento documento = new Documento();
        documento.setNombreOriginal(request.nombreOriginal());
        documento.setRutaAlmacenamiento(request.rutaAlmacenamiento());
        documento.setTipoDocumento(request.tipoDocumento());
        documento.setEstadoDocumento(request.estadoDocumento() != null ? request.estadoDocumento() : EstadoDocumento.PENDIENTE);
        documento.setObservaciones(request.observaciones());
        return documento;
    }
}
