package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.BadRequestException;
import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.EmpresaServis;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")

public class EmpresaController {

    @Autowired
    private EmpresaServis empresaServis;

    @PostMapping
    public ResponseEntity<EmpresaDtoRes> create(@RequestBody @Valid EmpresaDtoReq empresaDtoReq) {
        EmpresaDtoRes nueva = empresaServis.createEmpresas(empresaDtoReq);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> getById(@PathVariable Long id) {
        EmpresaDtoRes empresa = empresaServis.getEmpresasById(id);
        if (empresa == null) {
            throw new ResourceNotFoundException("empresa", "id", id);
        }
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> update(@PathVariable Long id, @RequestBody @Valid EmpresaDtoReq empresaDtoReq) {
        try {
            EmpresaDtoRes actualizada = empresaServis.updateEmpresa(id, empresaDtoReq);
            return ResponseEntity.ok(actualizada);
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            empresaServis.deleteEmpresas(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException error) {
            throw new BadRequestException(error.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDtoRes>> getAll() {
        List<EmpresaDtoRes> empresas = empresaServis.getAllEmpresas();
        if (empresas == null || empresas.isEmpty()) {
            throw new ResourceNotFoundException("empresas");
        }
        return ResponseEntity.ok(empresas);
    }
}

