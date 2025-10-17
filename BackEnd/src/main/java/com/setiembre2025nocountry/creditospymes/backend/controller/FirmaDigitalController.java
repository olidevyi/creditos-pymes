package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firmas-digitales")
public class FirmaDigitalController {

    @Autowired
    private FirmaDigitalServis firmaDigitalServis;

    @PostMapping
    public ResponseEntity<FirmaDigitalDtoRes> create(@RequestBody @Valid FirmaDigitalDtoReq request) {
        FirmaDigitalDtoRes nueva = firmaDigitalServis.createFirmaDigital(request);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> getById(@PathVariable Long id) {
        FirmaDigitalDtoRes firma = firmaDigitalServis.getFirmaDigitalById(id);
        if (firma == null) {
            throw new ResourceNotFoundException("firma", "id", id);
        }
        return ResponseEntity.ok(firma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> update(@PathVariable Long id, @RequestBody @Valid FirmaDigitalDtoReq request) {
        try {
            FirmaDigitalDtoRes actualizada = firmaDigitalServis.updateFirmaDigital(id, request);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            firmaDigitalServis.deleteFirmaDigital(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FirmaDigitalDtoRes>> getAll() {
        List<FirmaDigitalDtoRes> firmas = firmaDigitalServis.getAllFirmasDigitales();
        if (firmas == null || firmas.isEmpty()) {
            throw new ResourceNotFoundException("firmas");
        }
        return ResponseEntity.ok(firmas);
    }
}
