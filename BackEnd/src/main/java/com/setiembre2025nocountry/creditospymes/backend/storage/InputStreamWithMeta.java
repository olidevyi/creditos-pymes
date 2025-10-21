package com.setiembre2025nocountry.creditospymes.backend.storage;

import java.io.InputStream;

public record InputStreamWithMeta(InputStream stream, long length, String contentType) {
}
