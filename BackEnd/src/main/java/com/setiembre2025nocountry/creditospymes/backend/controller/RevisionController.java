package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionServis;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones")
public class RevisionController {

    @Autowired
    private RevisionServis revisionServis;

    @PostMapping
    public ResponseEntity<RevisionDtoRes> create(@RequestBody @Valid RevisionDtoReq request) {
        RevisionDtoRes nueva = revisionServis.createRevision(request);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> getById(@PathVariable Long id) {
        RevisionDtoRes revision = revisionServis.getRevisionById(id);
        if (revision == null) {
            throw new ResourceNotFoundException("revision", "id", id);
        }
        return ResponseEntity.ok(revision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> update(@PathVariable Long id, @RequestBody @Valid RevisionDtoReq request) {
        try {
            RevisionDtoRes actualizada = revisionServis.updateRevision(id, request);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            revisionServis.deleteRevision(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<RevisionDtoRes>> getAll() {
        List<RevisionDtoRes> revisiones = revisionServis.getAllRevisiones();
        if (revisiones == null || revisiones.isEmpty()) {
            throw new ResourceNotFoundException("revisiones");
        }
        return ResponseEntity.ok(revisiones);
    }
}
