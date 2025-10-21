package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.DocumentoDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.DocumentoDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.storage.InputStreamWithMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentoServis {

    public DocumentoDtoRes createDocumento(DocumentoDtoReq meta);

    DocumentoDtoRes upload(MultipartFile file, DocumentoDtoReq meta) throws IOException;

    DocumentoDtoRes getDocumentoById(Long id);

    List<DocumentoDtoRes> getAllDocumentos();

    DocumentoDtoRes updateDocumentoMeta(Long id, DocumentoDtoReq meta);

    DocumentoDtoRes replaceFile(Long id, MultipartFile file) throws IOException;

    void deleteDocumento(Long id);

    InputStreamWithMeta downloadStream(Long id) throws IOException;
}
