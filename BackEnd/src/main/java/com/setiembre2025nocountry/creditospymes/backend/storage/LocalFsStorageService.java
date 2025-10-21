package com.setiembre2025nocountry.creditospymes.backend.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class LocalFsStorageService implements StorageService {

    @Value("${app.storage.root}")
    private String rootDir;

    @Override
    public StoredObject store(InputStream in, long length, String contentType, String originalName) throws IOException {
        Path root = Paths.get(rootDir);
        Path tmpDir = root.resolve("tmp");
        Files.createDirectories(tmpDir);

        // 1) Copiar a temp + calcular sha256 en una pasada
        MessageDigest md = newMessageDigest();
        Path tempFile = tmpDir.resolve(UUID.randomUUID().toString());
        try (DigestInputStream din = new DigestInputStream(in, md);
             OutputStream out = Files.newOutputStream(tempFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            din.transferTo(out);
        }
        String sha256 = hex(md.digest());

        // 2) Derivar key final por hash + extensión
        String ext = extensionOf(originalName);
        String key = sha256.substring(0, 2) + "/" + sha256.substring(2, 4) + "/" + sha256 + ext;

        // 3) Mover a destino definitivo
        Path target = root.resolve(key);
        Files.createDirectories(target.getParent());
        if (Files.exists(target)) {
            // Ya existe un objeto con ese hash: descartar temp y usar existente
            Files.deleteIfExists(tempFile);
        } else {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        }

        long finalLen = Files.size(root.resolve(key));
        return new StoredObject(key, finalLen, contentType, sha256);
    }

    @Override
    public InputStreamWithMeta load(String key) throws IOException {
        Path root = Paths.get(rootDir);
        Path p = root.resolve(key);
        if (!Files.exists(p)) throw new NoSuchFileException("Objeto no encontrado: " + key);
        String ct = Files.probeContentType(p);
        long len = Files.size(p);
        return new InputStreamWithMeta(Files.newInputStream(p, StandardOpenOption.READ), len,
                ct != null ? ct : "application/octet-stream");
    }

    @Override
    public void delete(String key) throws IOException {
        Path root = Paths.get(rootDir);
        Files.deleteIfExists(root.resolve(key));
    }

    private static MessageDigest newMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    private static String extensionOf(String name) {
        if (name == null) return "";
        int i = name.lastIndexOf('.');
        return (i >= 0 && i < name.length() - 1) ? name.substring(i).toLowerCase() : "";
    }

    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
