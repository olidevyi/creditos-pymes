// SolicitudCreditoController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.SolicitudCreditoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.SolicitudCreditoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.SolicitudCreditoServis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes de Crédito", description = "ABM de solicitudes")
public class SolicitudCreditoController {

    private final SolicitudCreditoServis solicitudCreditoServis;

    public SolicitudCreditoController(SolicitudCreditoServis solicitudCreditoServis) {
        this.solicitudCreditoServis = solicitudCreditoServis;
    }

    @Operation(summary = "Crear solicitud",
            responses = @ApiResponse(responseCode = "200", description = "Creada",
                    content = @Content(schema = @Schema(implementation = SolicitudCreditoDtoRes.class))))
    @PostMapping
    public ResponseEntity<SolicitudCreditoDtoRes> createSolicitud(@Valid @RequestBody SolicitudCreditoDtoReq solicitudDtoReq) {
        SolicitudCreditoDtoRes nuevaSolicitud = solicitudCreditoServis.createSolicitud(solicitudDtoReq);
        return ResponseEntity.ok(nuevaSolicitud);
    }

    @Operation(summary = "Obtener solicitud por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = SolicitudCreditoDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCreditoDtoRes> getSolicitudById(@PathVariable Long id) {
        try {
            SolicitudCreditoDtoRes solicitud = solicitudCreditoServis.getSolicitudById(id);
            if (solicitud == null) throw new ResourceNotFoundException("solicitud", "id", id);
            return ResponseEntity.ok(solicitud);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar solicitud",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizada",
                            content = @Content(schema = @Schema(implementation = SolicitudCreditoDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudCreditoDtoRes> updateSolicitud(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudCreditoDtoReq solicitudDtoReq) throws ChangeSetPersister.NotFoundException {
        SolicitudCreditoDtoRes actualizada = solicitudCreditoServis.updateSolicitud(id, solicitudDtoReq);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar solicitud", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Long id) {
        try {
            solicitudCreditoServis.deleteSolicitud(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar solicitudes",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SolicitudCreditoDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<SolicitudCreditoDtoRes>> getAllSolicitudes() {
        List<SolicitudCreditoDtoRes> solicitudes = solicitudCreditoServis.getAllSolicitud();
        if (solicitudes == null || solicitudes.isEmpty()) throw new ResourceNotFoundException("solicitudes");
        return ResponseEntity.ok(solicitudes);
    }
}
