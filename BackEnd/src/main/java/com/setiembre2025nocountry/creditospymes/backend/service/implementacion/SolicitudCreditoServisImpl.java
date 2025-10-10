package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.SolicitudCreditoMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.SolicitudCreditoServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudCreditoServisImpl implements SolicitudCreditoServis {

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private SolicitudCreditoMapper solicitudCreditoMapper;

    @Override
    public SolicitudCreditoDtoRes createSolicitud(SolicitudCreditoDtoReq solicitudDTOReq) {
        SolicitudCredito solicitud = solicitudCreditoMapper.toEntity(solicitudDTOReq);
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
        solicitudExistente.setEstadoSolicitud(solicitudDTOReq.estadoSolicitud());
        solicitudExistente.setProposito(solicitudDTOReq.proposito());
        //solicitudExistente.setEmpresa(solicitudCreditoMapper.mapEmpresa(solicitudDTOReq.empresaId()));

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
}
