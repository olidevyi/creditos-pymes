package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.service.DocumentoServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
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
public class DocumentoController {

    private final DocumentoServis documentoServis;

    public DocumentoController(DocumentoServis documentoServis) {
        this.documentoServis = documentoServis;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoDtoRes> upload(
            @RequestPart("file") MultipartFile file,
            @Valid @ModelAttribute DocumentoDtoReq meta) throws IOException {
        return ResponseEntity.ok(documentoServis.upload(file, meta));
    }

    @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoDtoRes> replaceFile(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(documentoServis.replaceFile(id, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> updateMeta(
            @PathVariable Long id,
            @Valid @RequestBody DocumentoDtoReq meta) {
        return ResponseEntity.ok(documentoServis.updateDocumentoMeta(id, meta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDtoRes> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentoServis.getDocumentoById(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDtoRes>> getAll() {
        return ResponseEntity.ok(documentoServis.getAllDocumentos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoServis.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }

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
