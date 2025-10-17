package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionSolicitudDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.RevisionSolicitudServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones-solicitud")
public class RevisionSolicitudController {

    @Autowired
    private RevisionSolicitudServis revisionSolicitudServis;

    @PostMapping
    public ResponseEntity<RevisionSolicitudDtoRes> create(@RequestBody RevisionSolicitudDtoReq request) {
        RevisionSolicitudDtoRes nueva = revisionSolicitudServis.createRevisionSolicitud(request);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevisionSolicitudDtoRes> getById(@PathVariable Long id) {
        RevisionSolicitudDtoRes revision = revisionSolicitudServis.getRevisionSolicitudById(id);
        if (revision == null) {
            throw new ResourceNotFoundException("revision", "id", id);
        }
        return ResponseEntity.ok(revision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevisionSolicitudDtoRes> update(@PathVariable Long id, @RequestBody RevisionSolicitudDtoReq request) {
        try {
            RevisionSolicitudDtoRes actualizada = revisionSolicitudServis.updateRevisionSolicitud(id, request);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            revisionSolicitudServis.deleteRevisionSolicitud(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<RevisionSolicitudDtoRes>> getAll() {
        List<RevisionSolicitudDtoRes> revisiones = revisionSolicitudServis.getAllRevisionesSolicitud();
        if (revisiones == null || revisiones.isEmpty()) {
            throw new ResourceNotFoundException("revisiones");
        }
        return ResponseEntity.ok(revisiones);
    }
}
