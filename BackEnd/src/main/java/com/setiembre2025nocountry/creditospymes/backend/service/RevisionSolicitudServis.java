package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.RevisionSolicitudDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.RevisionSolicitudDtoReq;

import java.util.List;

public interface RevisionSolicitudServis {

    RevisionSolicitudDtoRes createRevisionSolicitud(RevisionSolicitudDtoReq request);

    RevisionSolicitudDtoRes getRevisionSolicitudById(Long id);

    RevisionSolicitudDtoRes updateRevisionSolicitud(Long id, RevisionSolicitudDtoReq request);

    void deleteRevisionSolicitud(Long id);

    List<RevisionSolicitudDtoRes> getAllRevisionesSolicitud();
}
