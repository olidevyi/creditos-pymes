package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.EstadoSolicitud;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SolicitudCreditoMapper {

    private final DocumentoMapper documentoMapper;
    private final RevisionSolicitudMapper revisionSolicitudMapper;
    private final FirmaDigitalMapper firmaDigitalMapper;

    public SolicitudCreditoMapper(DocumentoMapper documentoMapper,
                                  RevisionSolicitudMapper revisionSolicitudMapper,
                                  FirmaDigitalMapper firmaDigitalMapper) {
        this.documentoMapper = documentoMapper;
        this.revisionSolicitudMapper = revisionSolicitudMapper;
        this.firmaDigitalMapper = firmaDigitalMapper;
    }

    public SolicitudCreditoDtoRes toDto(SolicitudCredito solicitud) {
        Long solicitanteId = solicitud.getSolicitante() != null ? solicitud.getSolicitante().getId() : null;
        String solicitanteNombre = solicitud.getSolicitante() != null
                ? solicitud.getSolicitante().getNombre() + " " + solicitud.getSolicitante().getApellido()
                : null;
        Long empresaId = solicitud.getEmpresa() != null ? solicitud.getEmpresa().getId() : null;
        String empresaNombre = solicitud.getEmpresa() != null ? solicitud.getEmpresa().getNombre() : null;

        return new SolicitudCreditoDtoRes(
                solicitud.getId(),
                solicitud.getMontoSolicitado(),
                solicitud.getPlazo(),
                solicitud.getProposito(),
                solicitud.getEstadoSolicitud(),
                solicitanteId,
                solicitanteNombre,
                empresaId,
                empresaNombre,
                solicitud.getFechaEstimadaDesembolso(),
                solicitud.getComentariosAdicionales(),
                solicitud.getFechaCreacion(),
                solicitud.getFechaActualizacion(),
                solicitud.getFechaEnvio(),
                mapDocumentos(solicitud),
                mapRevisiones(solicitud),
                mapFirmas(solicitud)
        );
    }

    public SolicitudCredito toEntity(SolicitudCreditoDtoReq request) {
        SolicitudCredito solicitud = new SolicitudCredito();
        solicitud.setMontoSolicitado(request.montoSolicitado());
        solicitud.setPlazo(request.plazo());
        solicitud.setProposito(request.proposito());
        solicitud.setEstadoSolicitud(request.estadoSolicitud() != null ? request.estadoSolicitud() : EstadoSolicitud.BORRADOR);
        solicitud.setFechaEstimadaDesembolso(request.fechaEstimadaDesembolso());
        solicitud.setComentariosAdicionales(request.comentariosAdicionales());
        solicitud.setFechaEnvio(request.fechaEnvio());
        return solicitud;
    }

    private Set<DocumentoDtoRes> mapDocumentos(SolicitudCredito solicitud) {
        if (solicitud.getDocumentos() == null || solicitud.getDocumentos().isEmpty()) {
            return Set.of();
        }
        return solicitud.getDocumentos().stream()
                .map(documentoMapper::toDto)
                .collect(Collectors.toSet());
    }

    private Set<RevisionSolicitudDtoRes> mapRevisiones(SolicitudCredito solicitud) {
        if (solicitud.getRevisiones() == null || solicitud.getRevisiones().isEmpty()) {
            return Set.of();
        }
        return solicitud.getRevisiones().stream()
                .map(revisionSolicitudMapper::toDto)
                .collect(Collectors.toSet());
    }

    private Set<FirmaDigitalDtoRes> mapFirmas(SolicitudCredito solicitud) {
        if (solicitud.getFirmasDigitales() == null || solicitud.getFirmasDigitales().isEmpty()) {
            return Set.of();
        }
        return solicitud.getFirmasDigitales().stream()
                .map(firmaDigitalMapper::toDto)
                .collect(Collectors.toSet());
    }
}
