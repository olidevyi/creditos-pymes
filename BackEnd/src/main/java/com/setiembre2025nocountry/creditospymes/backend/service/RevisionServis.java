package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionDtoReq;

import java.util.List;

public interface RevisionServis {

    RevisionDtoRes createRevision(RevisionDtoReq request);

    RevisionDtoRes getRevisionById(Long id);

    RevisionDtoRes updateRevision(Long id, RevisionDtoReq request);

    void deleteRevision(Long id);

    List<RevisionDtoRes> getAllRevisiones();
}
