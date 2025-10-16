package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones")
public class RevisionController {

    @Autowired
    private RevisionServis revisionServis;

    @PostMapping
    public ResponseEntity<RevisionDtoRes> create(@RequestBody RevisionDtoReq request) {
        RevisionDtoRes nueva = revisionServis.createRevision(request);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> getById(@PathVariable Long id) {
        RevisionDtoRes revision = revisionServis.getRevisionById(id);
        return ResponseEntity.ok(revision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevisionDtoRes> update(@PathVariable Long id, @RequestBody RevisionDtoReq request) {
        RevisionDtoRes actualizada = revisionServis.updateRevision(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        revisionServis.deleteRevision(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RevisionDtoRes>> getAll() {
        List<RevisionDtoRes> revisiones = revisionServis.getAllRevisiones();
        return ResponseEntity.ok(revisiones);
    }
}
