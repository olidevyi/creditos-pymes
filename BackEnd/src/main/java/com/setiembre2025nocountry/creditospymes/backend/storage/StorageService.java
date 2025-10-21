package com.setiembre2025nocountry.creditospymes.backend.storage;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    StoredObject store(InputStream in, long length, String contentType, String originalName) throws IOException;

    InputStreamWithMeta load(String key) throws IOException;

    void delete(String key) throws IOException;
}
