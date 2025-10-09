package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface SolicitudCreditoServis {
    // metodo para crear la solicitud
    SolicitudCreditoDtoRes createSolicitud(SolicitudCreditoDtoReq userDTOReq);

    // Firma del método para obtener una solicitud por su ID
    SolicitudCreditoDtoRes getSolicitudById(Long id) throws ChangeSetPersister.NotFoundException;

    // método para actualizar una solicitud
    SolicitudCreditoDtoRes updateSolicitud(Long id, SolicitudCreditoDtoReq userDTOReq) throws ChangeSetPersister.NotFoundException;

    // método para eliminar una solicitud por su ID
    void deleteSolicitud(Long id) throws ChangeSetPersister.NotFoundException;

    // método para obtener todas la solicitudes
    List<SolicitudCreditoDtoRes> getAllSolicitud();
}