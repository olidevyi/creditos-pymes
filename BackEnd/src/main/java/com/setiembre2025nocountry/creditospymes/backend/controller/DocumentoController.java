package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.DocumentoServis;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoServis documentoServis;

    @PostMapping
    public ResponseEntity<DocumentoDtoRes> create(@RequestBody @Valid DocumentoDtoReq request) {
        DocumentoDtoRes nuevo = documentoServis.createDocumento(request);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> getById(@PathVariable Long id) {
        DocumentoDtoRes documento = documentoServis.getDocumentoById(id);
        if (documento == null) {
            throw new ResourceNotFoundException("documento", "id", id);
        }
        return ResponseEntity.ok(documento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> update(@PathVariable Long id, @RequestBody DocumentoDtoReq request) {
        try {
            DocumentoDtoRes actualizado = documentoServis.updateDocumento(id, request);
            return ResponseEntity.ok(actualizado);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            documentoServis.deleteDocumento(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDtoRes>> getAll() {
        List<DocumentoDtoRes> documentos = documentoServis.getAllDocumentos();
        if (documentos == null || documentos.isEmpty()) {
            throw new ResourceNotFoundException("documentos");
        }
        return ResponseEntity.ok(documentos);
    }
}
