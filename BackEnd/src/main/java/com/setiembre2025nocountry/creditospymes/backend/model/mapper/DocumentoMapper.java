package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoDocumento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Documento;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoDtoRes toDto(Documento d) {
        Long solicitudId = d.getSolicitudCredito() != null ? d.getSolicitudCredito().getId() : null;
        Long cargadoPorId = d.getCargadoPor() != null ? d.getCargadoPor().getId() : null;
        String cargadoPorNombre = d.getCargadoPor() != null
                ? (d.getCargadoPor().getNombre() + " " + d.getCargadoPor().getApellido()).trim()
                : null;

        return new DocumentoDtoRes(
                d.getId(),
                d.getNombreOriginal(),
                d.getTipoContenido(),
                d.getTamano(),
                d.getSha256(),
                d.getTipoDocumento(),
                d.getEstadoDocumento(),
                d.getObservaciones(),
                d.getFechaCarga(),
                solicitudId,
                cargadoPorId,
                cargadoPorNombre
        );
    }

    public void applyMeta(Documento d, DocumentoDtoReq req) {
        d.setTipoDocumento(req.tipoDocumento());
        d.setEstadoDocumento(req.estadoDocumento() != null ? req.estadoDocumento() : EstadoDocumento.PENDIENTE);
        d.setObservaciones(req.observaciones());
    }

    public void fillFileAndRefs(Documento d,
                                String nombreOriginal,
                                String tipoContenido,
                                long tamano,
                                String storageKey,
                                String sha256,
                                SolicitudCredito solicitud,
                                Usuario cargadoPor) {
        d.setNombreOriginal(nombreOriginal);
        d.setTipoContenido(tipoContenido);
        d.setTamano(tamano);
        d.setStorageKey(storageKey);
        d.setSha256(sha256);
        d.setSolicitudCredito(solicitud);
        d.setCargadoPor(cargadoPor);
    }
}
