// src/main/java/com/setiembre2025nocountry/creditospymes/backend/controller/FirmaDigitalController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaInitRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaCompleteReq;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaInitReq;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/firmas")
public class FirmaDigitalController {

    private final FirmaDigitalServis service;

    public FirmaDigitalController(FirmaDigitalServis service) {
        this.service = service;
    }

    @PostMapping("/init")
    public ResponseEntity<FirmaInitRes> init(@RequestBody FirmaInitReq req, HttpServletRequest http) {
        String ip = http.getRemoteAddr();
        String ua = http.getHeader("User-Agent");
        return ResponseEntity.ok(service.init(req, ip, ua));
    }

    @PostMapping("/complete")
    public ResponseEntity<FirmaDigitalDtoRes> complete(@RequestBody FirmaCompleteReq req) throws IOException {
        return ResponseEntity.ok(service.complete(req));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Map<String, Object>> verify(@PathVariable Long id) throws IOException {
        boolean ok = service.verify(id);
        return ResponseEntity.ok(Map.of("firmaId", id, "valida", ok));
    }

    @PostMapping("/{id}/revoke")
    public ResponseEntity<Void> revoke(@PathVariable Long id,
                                       @RequestParam(required = false) Long actorId,
                                       @RequestParam(required = false) String motivo) {
        service.revoke(id, actorId, motivo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FirmaDigitalDtoRes>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) throws IOException {
        InputStreamWithMeta s = service.download(id);
        String filename = URLEncoder.encode("firma-" + id, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        s.contentType() != null ? s.contentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                .contentLength(s.length())
                .body(new InputStreamResource(s.stream()));
    }
}
