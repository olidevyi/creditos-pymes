package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.EmpresaServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")

public class EmpresaController {

    @Autowired
    private EmpresaServis empresaServis;

    @PostMapping
    public ResponseEntity<EmpresaDtoRes> create(@RequestBody EmpresaDtoReq empresaDtoReq) {
        EmpresaDtoRes nueva = empresaServis.createEmpresas(empresaDtoReq);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> getById(@PathVariable Long id) {
        EmpresaDtoRes empresa = empresaServis.getEmpresasById(id);
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDtoRes> update(@PathVariable Long id, @RequestBody EmpresaDtoReq empresaDtoReq) {
        EmpresaDtoRes actualizada = empresaServis.updateEmpresa(id, empresaDtoReq);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empresaServis.deleteEmpresas(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDtoRes>> getAll() {
        List<EmpresaDtoRes> empresas = empresaServis.getAllEmpresas();
        return ResponseEntity.ok(empresas);
    }
}

