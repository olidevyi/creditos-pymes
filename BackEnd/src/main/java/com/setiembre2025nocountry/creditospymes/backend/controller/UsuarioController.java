package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.exception.ResourceNotFoundException;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.UsuarioServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServis usuarioServis;

    //Crear usuario
    @PostMapping
    public ResponseEntity<UsuarioDtoRes> createUser(@RequestBody UsuarioDtoReq usuarioDtoReq) {
        UsuarioDtoRes nuevoUsuario = usuarioServis.createUser(usuarioDtoReq);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoRes> getUserById(@PathVariable Long id) {
        try {
            UsuarioDtoRes usuario = usuarioServis.getUserById(id);
            if (usuario == null) {
                throw new ResourceNotFoundException("usuario", "id", id);
            }
            return ResponseEntity.ok(usuario);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoRes> updateUser(@PathVariable Long id, @RequestBody UsuarioDtoReq usuarioDtoReq) {
        try {
            UsuarioDtoRes actualizado = usuarioServis.updateUser(id, usuarioDtoReq);
            return ResponseEntity.ok(actualizado);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            usuarioServis.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDtoRes>> getAllUsers() {
        List<UsuarioDtoRes> usuarios = usuarioServis.getAllUsers();
        if (usuarios == null || usuarios.isEmpty()) {
            throw new ResourceNotFoundException("usuarios");
        }
        return ResponseEntity.ok(usuarios);
    }
}
