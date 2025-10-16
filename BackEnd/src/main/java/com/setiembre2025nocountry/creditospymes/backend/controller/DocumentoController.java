package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.DocumentoServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoServis documentoServis;

    @PostMapping
    public ResponseEntity<DocumentoDtoRes> create(@RequestBody DocumentoDtoReq request) {
        DocumentoDtoRes nuevo = documentoServis.createDocumento(request);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> getById(@PathVariable Long id) {
        DocumentoDtoRes documento = documentoServis.getDocumentoById(id);
        return ResponseEntity.ok(documento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> update(@PathVariable Long id, @RequestBody DocumentoDtoReq request) {
        DocumentoDtoRes actualizado = documentoServis.updateDocumento(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoServis.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDtoRes>> getAll() {
        List<DocumentoDtoRes> documentos = documentoServis.getAllDocumentos();
        return ResponseEntity.ok(documentos);
    }
}
