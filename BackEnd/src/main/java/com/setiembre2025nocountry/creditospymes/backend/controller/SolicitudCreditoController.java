package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.SolicitudCreditoServis;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudCreditoController {

    @Autowired
    private SolicitudCreditoServis solicitudCreditoServis;

    // Crear solicitud de crédito
    @PostMapping
    public ResponseEntity<SolicitudCreditoDtoRes> createSolicitud(@RequestBody @Valid SolicitudCreditoDtoReq solicitudDtoReq) {
        SolicitudCreditoDtoRes nuevaSolicitud = solicitudCreditoServis.createSolicitud(solicitudDtoReq);
        return ResponseEntity.ok(nuevaSolicitud);
    }

    // Obtener solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCreditoDtoRes> getSolicitudById(@PathVariable Long id) {
        try {
            SolicitudCreditoDtoRes solicitud = solicitudCreditoServis.getSolicitudById(id);
            if (solicitud == null) {
                throw new ResourceNotFoundException("solicitud", "id", id);
            }
            return ResponseEntity.ok(solicitud);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar solicitud
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudCreditoDtoRes> updateSolicitud(
            @PathVariable Long id,
            @RequestBody @Valid SolicitudCreditoDtoReq solicitudDtoReq) throws ChangeSetPersister.NotFoundException {
        try {
            SolicitudCreditoDtoRes actualizada = solicitudCreditoServis.updateSolicitud(id, solicitudDtoReq);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    //Eliminar solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Long id) {
        try {
            solicitudCreditoServis.deleteSolicitud(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todas las solicitudes
    @GetMapping
    public ResponseEntity<List<SolicitudCreditoDtoRes>> getAllSolicitudes() {
        List<SolicitudCreditoDtoRes> solicitudes = solicitudCreditoServis.getAllSolicitud();
        if (solicitudes == null || solicitudes.isEmpty()) {
            throw new ResourceNotFoundException("solicitudes");
        }
        return ResponseEntity.ok(solicitudes);
    }
}
