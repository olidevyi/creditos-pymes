// RevisionSolicitudController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionSolicitudDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionSolicitudServis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones-solicitud")
@Tag(name = "Revisiones de Solicitud", description = "ABM de revisiones por solicitud")
public class RevisionSolicitudController {

    private final RevisionSolicitudServis revisionSolicitudServis;

    public RevisionSolicitudController(RevisionSolicitudServis revisionSolicitudServis) {
        this.revisionSolicitudServis = revisionSolicitudServis;
    }

    @Operation(summary = "Crear revisión de solicitud",
            responses = @ApiResponse(responseCode = "200", description = "Creada",
                    content = @Content(schema = @Schema(implementation = RevisionSolicitudDtoRes.class))))
    @PostMapping
    public ResponseEntity<RevisionSolicitudDtoRes> create(@Valid @RequestBody RevisionSolicitudDtoReq request) {
        RevisionSolicitudDtoRes nueva = revisionSolicitudServis.createRevisionSolicitud(request);
        return ResponseEntity.ok(nueva);
    }

    @Operation(summary = "Obtener revisión de solicitud por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = RevisionSolicitudDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<RevisionSolicitudDtoRes> getById(@PathVariable Long id) {
        RevisionSolicitudDtoRes revision = revisionSolicitudServis.getRevisionSolicitudById(id);
        if (revision == null) throw new ResourceNotFoundException("revision", "id", id);
        return ResponseEntity.ok(revision);
    }

    @Operation(summary = "Actualizar revisión de solicitud",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizada",
                            content = @Content(schema = @Schema(implementation = RevisionSolicitudDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<RevisionSolicitudDtoRes> update(@PathVariable Long id, @Valid @RequestBody RevisionSolicitudDtoReq request) {
        try {
            RevisionSolicitudDtoRes actualizada = revisionSolicitudServis.updateRevisionSolicitud(id, request);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar revisión de solicitud", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            revisionSolicitudServis.deleteRevisionSolicitud(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Listar revisiones de solicitud",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RevisionSolicitudDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<RevisionSolicitudDtoRes>> getAll() {
        List<RevisionSolicitudDtoRes> revisiones = revisionSolicitudServis.getAllRevisionesSolicitud();
        if (revisiones == null || revisiones.isEmpty()) throw new ResourceNotFoundException("revisiones");
        return ResponseEntity.ok(revisiones);
    }
}
