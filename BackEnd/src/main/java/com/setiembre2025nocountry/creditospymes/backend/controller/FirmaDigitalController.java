package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firmas-digitales")
public class FirmaDigitalController {

    @Autowired
    private FirmaDigitalServis firmaDigitalServis;

    @PostMapping
    public ResponseEntity<FirmaDigitalDtoRes> create(@RequestBody FirmaDigitalDtoReq request) {
        FirmaDigitalDtoRes nueva = firmaDigitalServis.createFirmaDigital(request);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> getById(@PathVariable Long id) {
        FirmaDigitalDtoRes firma = firmaDigitalServis.getFirmaDigitalById(id);
        return ResponseEntity.ok(firma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> update(@PathVariable Long id, @RequestBody FirmaDigitalDtoReq request) {
        FirmaDigitalDtoRes actualizada = firmaDigitalServis.updateFirmaDigital(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        firmaDigitalServis.deleteFirmaDigital(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FirmaDigitalDtoRes>> getAll() {
        List<FirmaDigitalDtoRes> firmas = firmaDigitalServis.getAllFirmasDigitales();
        return ResponseEntity.ok(firmas);
    }
}
