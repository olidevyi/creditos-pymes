// FirmaDigitalController.java
package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaInitRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaCompleteReq;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaInitReq;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/firmas")
@Tag(name = "Firmas Digitales", description = "Flujo de firma, verificación y descarga")
public class FirmaDigitalController {

    private final FirmaDigitalServis service;

    public FirmaDigitalController(FirmaDigitalServis service) {
        this.service = service;
    }

    @Operation(summary = "Iniciar firma",
            responses = @ApiResponse(responseCode = "200", description = "Inicio OK",
                    content = @Content(schema = @Schema(implementation = FirmaInitRes.class))))
    @PostMapping("/init")
    public ResponseEntity<FirmaInitRes> init(@RequestBody FirmaInitReq req, HttpServletRequest http) {
        String ip = http.getRemoteAddr();
        String ua = http.getHeader("User-Agent");
        return ResponseEntity.ok(service.init(req, ip, ua));
    }

    @Operation(summary = "Completar firma",
            responses = @ApiResponse(responseCode = "200", description = "Firma completa",
                    content = @Content(schema = @Schema(implementation = FirmaDigitalDtoRes.class))))
    @PostMapping("/complete")
    public ResponseEntity<FirmaDigitalDtoRes> complete(@RequestBody FirmaCompleteReq req) throws IOException {
        return ResponseEntity.ok(service.complete(req));
    }

    @Operation(summary = "Verificar firma",
            responses = @ApiResponse(responseCode = "200", description = "Resultado verificación"))
    @GetMapping("/{id}/verify")
    public ResponseEntity<Map<String, Object>> verify(@PathVariable Long id) throws IOException {
        boolean ok = service.verify(id);
        return ResponseEntity.ok(Map.of("firmaId", id, "valida", ok));
    }

    @Operation(summary = "Revocar firma", responses = @ApiResponse(responseCode = "204", description = "Revocada"))
    @PostMapping("/{id}/revoke")
    public ResponseEntity<Void> revoke(@PathVariable Long id,
                                       @RequestParam(required = false) Long actorId,
                                       @RequestParam(required = false) String motivo) {
        service.revoke(id, actorId, motivo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener firma por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = FirmaDigitalDtoRes.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<FirmaDigitalDtoRes> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Listar firmas")
    @GetMapping
    public ResponseEntity<List<FirmaDigitalDtoRes>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "Descargar contenedor firmado",
            responses = @ApiResponse(responseCode = "200", description = "Binario",
                    content = @Content(mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary"))))
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
