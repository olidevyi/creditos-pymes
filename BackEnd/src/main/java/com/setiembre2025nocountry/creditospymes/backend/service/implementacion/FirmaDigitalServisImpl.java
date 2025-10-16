package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.FirmaDigital;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.SolicitudCredito;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.FirmaDigitalMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.FirmaDigitalRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.SolicitudCreditoRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FirmaDigitalServisImpl implements FirmaDigitalServis {

    @Autowired
    private FirmaDigitalRepository firmaDigitalRepository;

    @Autowired
    private FirmaDigitalMapper firmaDigitalMapper;

    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public FirmaDigitalDtoRes createFirmaDigital(FirmaDigitalDtoReq request) {
        FirmaDigital firmaDigital = firmaDigitalMapper.toEntity(request);
        firmaDigital.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        firmaDigital.setFirmante(obtenerUsuario(request.firmanteId()));
        FirmaDigital saved = firmaDigitalRepository.save(firmaDigital);
        return firmaDigitalMapper.toDto(saved);
    }

    @Override
    public FirmaDigitalDtoRes getFirmaDigitalById(Long id) {
        FirmaDigital firmaDigital = firmaDigitalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Firma digital no encontrada con id: " + id));
        return firmaDigitalMapper.toDto(firmaDigital);
    }

    @Override
    public FirmaDigitalDtoRes updateFirmaDigital(Long id, FirmaDigitalDtoReq request) {
        FirmaDigital firmaDigital = firmaDigitalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Firma digital no encontrada con id: " + id));

        if (request.estado() != null) {
            firmaDigital.setEstado(request.estado());
        }
        firmaDigital.setHashFirma(request.hashFirma());
        firmaDigital.setIpFirmante(request.ipFirmante());
        firmaDigital.setDocumentoFirmadoUrl(request.documentoFirmadoUrl());

        if (firmaDigital.getSolicitudCredito() == null
                || !Objects.equals(firmaDigital.getSolicitudCredito().getId(), request.solicitudId())) {
            firmaDigital.setSolicitudCredito(obtenerSolicitud(request.solicitudId()));
        }

        if (firmaDigital.getFirmante() == null
                || !Objects.equals(firmaDigital.getFirmante().getId(), request.firmanteId())) {
            firmaDigital.setFirmante(obtenerUsuario(request.firmanteId()));
        }

        FirmaDigital actualizado = firmaDigitalRepository.save(firmaDigital);
        return firmaDigitalMapper.toDto(actualizado);
    }

    @Override
    public void deleteFirmaDigital(Long id) {
        FirmaDigital firmaDigital = firmaDigitalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Firma digital no encontrada con id: " + id));
        firmaDigitalRepository.delete(firmaDigital);
    }

    @Override
    public List<FirmaDigitalDtoRes> getAllFirmasDigitales() {
        return firmaDigitalRepository.findAll()
                .stream()
                .map(firmaDigitalMapper::toDto)
                .collect(Collectors.toList());
    }

    private SolicitudCredito obtenerSolicitud(Long solicitudId) {
        return solicitudCreditoRepository.findById(solicitudId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la solicitud con id " + solicitudId));
    }

    private Usuario obtenerUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id " + usuarioId));
    }
}
