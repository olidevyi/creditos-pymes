package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Empresa;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.SolicitudCreditoMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.EmpresaRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.SolicitudCreditoServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudCreditoServisImpl implements SolicitudCreditoServis {

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private SolicitudCreditoMapper solicitudCreditoMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public SolicitudCreditoDtoRes createSolicitud(SolicitudCreditoDtoReq solicitudDTOReq) {
        SolicitudCredito solicitud = solicitudCreditoMapper.toEntity(solicitudDTOReq);
        solicitud.setSolicitante(obtenerSolicitante(solicitudDTOReq.solicitanteId()));
        solicitud.setEmpresa(obtenerEmpresa(solicitudDTOReq.empresaId()));
        solicitud = solicitudCreditoRepository.save(solicitud);
        return solicitudCreditoMapper.toDto(solicitud);
    }

    @Override
    public SolicitudCreditoDtoRes getSolicitudById(Long id) throws ChangeSetPersister.NotFoundException {
        SolicitudCredito solicitud = solicitudCreditoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return solicitudCreditoMapper.toDto(solicitud);
    }

    @Override
    public SolicitudCreditoDtoRes updateSolicitud(Long id, SolicitudCreditoDtoReq solicitudDTOReq) throws ChangeSetPersister.NotFoundException {
        SolicitudCredito solicitudExistente = solicitudCreditoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        solicitudExistente.setMontoSolicitado(solicitudDTOReq.montoSolicitado());
        solicitudExistente.setPlazo(solicitudDTOReq.plazo());
        if (solicitudDTOReq.estadoSolicitud() != null) {
            solicitudExistente.setEstadoSolicitud(solicitudDTOReq.estadoSolicitud());
        }
        solicitudExistente.setProposito(solicitudDTOReq.proposito());
        solicitudExistente.setFechaEstimadaDesembolso(solicitudDTOReq.fechaEstimadaDesembolso());
        solicitudExistente.setComentariosAdicionales(solicitudDTOReq.comentariosAdicionales());
        solicitudExistente.setFechaEnvio(solicitudDTOReq.fechaEnvio());

        if (!Objects.equals(solicitudExistente.getSolicitante().getId(), solicitudDTOReq.solicitanteId())) {
            solicitudExistente.setSolicitante(obtenerSolicitante(solicitudDTOReq.solicitanteId()));
        }
        if (!Objects.equals(solicitudExistente.getEmpresa().getId(), solicitudDTOReq.empresaId())) {
            solicitudExistente.setEmpresa(obtenerEmpresa(solicitudDTOReq.empresaId()));
        }

        solicitudExistente = solicitudCreditoRepository.save(solicitudExistente);
        return solicitudCreditoMapper.toDto(solicitudExistente);
    }

    @Override
    public void deleteSolicitud(Long id) throws ChangeSetPersister.NotFoundException {
        SolicitudCredito solicitud = solicitudCreditoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        solicitudCreditoRepository.delete(solicitud);
    }

    @Override
    public List<SolicitudCreditoDtoRes> getAllSolicitud() {
        List<SolicitudCredito> solicitudes = solicitudCreditoRepository.findAll();
        return solicitudes.stream()
                .map(solicitudCreditoMapper::toDto)
                .collect(Collectors.toList());
    }

    private Usuario obtenerSolicitante(Long solicitanteId) {
        return usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario solicitante con id " + solicitanteId));
    }

    private Empresa obtenerEmpresa(Long empresaId) {
        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la empresa con id " + empresaId));
    }
}
