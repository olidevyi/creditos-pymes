// RevisionController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionServis;
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
@RequestMapping("/api/revisiones")
@Tag(name = "Revisiones", description = "ABM de revisiones")
public class RevisionController {

    private final RevisionServis revisionServis;

    public RevisionController(RevisionServis revisionServis) {
        this.revisionServis = revisionServis;
    }

    @Operation(summary = "Crear revisión",
            responses = @ApiResponse(responseCode = "200", description = "Creada",
                    content = @Content(schema = @Schema(implementation = RevisionDtoRes.class))))
    @PostMapping
    public ResponseEntity<RevisionDtoRes> create(@Valid @RequestBody RevisionDtoReq request) {
        RevisionDtoRes nueva = revisionServis.createRevision(request);
        return ResponseEntity.ok(nueva);
    }

    @Operation(summary = "Obtener revisión por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = RevisionDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> getById(@PathVariable Long id) {
        RevisionDtoRes revision = revisionServis.getRevisionById(id);
        if (revision == null) throw new ResourceNotFoundException("revision", "id", id);
        return ResponseEntity.ok(revision);
    }

    @Operation(summary = "Actualizar revisión",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizada",
                            content = @Content(schema = @Schema(implementation = RevisionDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> update(@PathVariable Long id, @Valid @RequestBody RevisionDtoReq request) {
        try {
            RevisionDtoRes actualizada = revisionServis.updateRevision(id, request);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar revisión", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            revisionServis.deleteRevision(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Listar revisiones",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RevisionDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<RevisionDtoRes>> getAll() {
        List<RevisionDtoRes> revisiones = revisionServis.getAllRevisiones();
        if (revisiones == null || revisiones.isEmpty()) throw new ResourceNotFoundException("revisiones");
        return ResponseEntity.ok(revisiones);
    }
}
