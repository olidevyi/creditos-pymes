package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.DocumentoServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@Tag(name = "Documentos", description = "Gestión de documentos y archivos")
public class DocumentoController {

    private final DocumentoServis documentoServis;

    public DocumentoController(DocumentoServis documentoServis) {
        this.documentoServis = documentoServis;
    }

    @Operation(
            summary = "Subir documento",
            description = "Carga un archivo y sus metadatos. Content-Type: multipart/form-data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Documento subido",
                            content = @Content(schema = @Schema(implementation = DocumentoDtoRes.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
            }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoDtoRes> upload(
            @RequestPart("file") MultipartFile file,
            @Valid @ModelAttribute DocumentoDtoReq meta) throws IOException {
        return ResponseEntity.ok(documentoServis.upload(file, meta));
    }

    @Operation(
            summary = "Reemplazar archivo de un documento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Archivo reemplazado",
                            content = @Content(schema = @Schema(implementation = DocumentoDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "Documento no encontrado")
            }
    )
    @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoDtoRes> replaceFile(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentoServis.replaceFile(id, file));
    }

    @Operation(
            summary = "Actualizar metadatos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Actualizado",
                            content = @Content(schema = @Schema(implementation = DocumentoDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> updateMeta(
            @PathVariable Long id,
            @Valid @RequestBody DocumentoDtoReq meta) {
        return ResponseEntity.ok(documentoServis.updateDocumentoMeta(id, meta));
    }

    @Operation(
            summary = "Obtener documento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = DocumentoDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentoServis.getDocumentoById(id));
    }

    @Operation(
            summary = "Listar documentos",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentoDtoRes.class)))))
    @GetMapping
    public ResponseEntity<List<DocumentoDtoRes>> getAll() {
        return ResponseEntity.ok(documentoServis.getAllDocumentos());
    }

    @Operation(summary = "Eliminar documento", responses = {
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoServis.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Descargar archivo del documento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Binario",
                            content = @Content(mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @GetMapping("/{id}/content")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) throws IOException {
        InputStreamWithMeta s = documentoServis.downloadStream(id);
        InputStreamResource body = new InputStreamResource(s.stream());
        String filename = URLEncoder.encode("documento-" + id, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        s.contentType() != null ? s.contentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                .contentLength(s.length())
                .body(body);
    }
}
