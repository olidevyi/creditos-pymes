package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.UsuarioServis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "ABM de usuarios")
public class UsuarioController {

    private final UsuarioServis usuarioServis;

    public UsuarioController(UsuarioServis usuarioServis) {
        this.usuarioServis = usuarioServis;
    }

    @Operation(summary = "Crear usuario",
            responses = @ApiResponse(responseCode = "200", description = "Creado",
                    content = @Content(schema = @Schema(implementation = UsuarioDtoRes.class))))
    @PostMapping
    public ResponseEntity<UsuarioDtoRes> createUser(@Valid @RequestBody UsuarioDtoReq usuarioDtoReq) {
        UsuarioDtoRes nuevoUsuario = usuarioServis.createUser(usuarioDtoReq);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @Operation(summary = "Obtener usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UsuarioDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoRes> getUserById(@PathVariable Long id) {
        try {
            UsuarioDtoRes usuario = usuarioServis.getUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizado",
                            content = @Content(schema = @Schema(implementation = UsuarioDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoRes> updateUser(@PathVariable Long id, @Valid @RequestBody UsuarioDtoReq usuarioDtoReq) {
        try {
            UsuarioDtoRes actualizado = usuarioServis.updateUser(id, usuarioDtoReq);
            return ResponseEntity.ok(actualizado);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar usuario", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            usuarioServis.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar usuarios",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<UsuarioDtoRes>> getAllUsers() {
        List<UsuarioDtoRes> usuarios = usuarioServis.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }
}
