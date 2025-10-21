// EmpresaController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.EmpresaServis;
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
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "ABM de empresas")
public class EmpresaController {

    private final EmpresaServis empresaServis;

    public EmpresaController(EmpresaServis empresaServis) {
        this.empresaServis = empresaServis;
    }

    @Operation(summary = "Crear empresa",
            responses = @ApiResponse(responseCode = "200", description = "Creada",
                    content = @Content(schema = @Schema(implementation = EmpresaDtoRes.class))))
    @PostMapping
    public ResponseEntity<EmpresaDtoRes> create(@Valid @RequestBody EmpresaDtoReq empresaDtoReq) {
        EmpresaDtoRes nueva = empresaServis.createEmpresas(empresaDtoReq);
        return ResponseEntity.ok(nueva);
    }

    @Operation(summary = "Obtener empresa por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = EmpresaDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> getById(@PathVariable Long id) {
        EmpresaDtoRes empresa = empresaServis.getEmpresasById(id);
        if (empresa == null) throw new ResourceNotFoundException("empresa", "id", id);
        return ResponseEntity.ok(empresa);
    }

    @Operation(summary = "Actualizar empresa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizada",
                            content = @Content(schema = @Schema(implementation = EmpresaDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> update(@PathVariable Long id, @Valid @RequestBody EmpresaDtoReq empresaDtoReq) {
        try {
            EmpresaDtoRes actualizada = empresaServis.updateEmpresa(id, empresaDtoReq);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar empresa", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            empresaServis.deleteEmpresas(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Listar empresas",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<EmpresaDtoRes>> getAll() {
        List<EmpresaDtoRes> empresas = empresaServis.getAllEmpresas();
        if (empresas == null || empresas.isEmpty()) throw new ResourceNotFoundException("empresas");
        return ResponseEntity.ok(empresas);
    }
}
