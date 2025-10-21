// src/main/java/com/setiembre2025nocountry/creditospymes/backend/service/FirmaDigitalServis.java
package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaInitRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaCompleteReq;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaInitReq;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;

import java.io.IOException;
import java.util.List;

public interface FirmaDigitalServis {
    FirmaInitRes init(FirmaInitReq req, String ip, String userAgent);
    FirmaDigitalDtoRes complete(FirmaCompleteReq req) throws IOException;
    boolean verify(Long firmaId) throws IOException;
    void revoke(Long firmaId, Long actorId, String motivo);
    FirmaDigitalDtoRes getById(Long id);
    List<FirmaDigitalDtoRes> getAll();
    InputStreamWithMeta download(Long firmaId) throws IOException;
}
