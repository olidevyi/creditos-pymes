package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;

import java.util.List;

public interface DocumentoServis {

    DocumentoDtoRes createDocumento(DocumentoDtoReq request);

    DocumentoDtoRes getDocumentoById(Long id);

    DocumentoDtoRes updateDocumento(Long id, DocumentoDtoReq request);

    void deleteDocumento(Long id);

    List<DocumentoDtoRes> getAllDocumentos();
}
