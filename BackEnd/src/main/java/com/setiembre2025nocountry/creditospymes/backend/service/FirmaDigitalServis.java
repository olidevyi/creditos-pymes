package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;

import java.util.List;

public interface FirmaDigitalServis {

    FirmaDigitalDtoRes createFirmaDigital(FirmaDigitalDtoReq request);

    FirmaDigitalDtoRes getFirmaDigitalById(Long id);

    FirmaDigitalDtoRes updateFirmaDigital(Long id, FirmaDigitalDtoReq request);

    void deleteFirmaDigital(Long id);

    List<FirmaDigitalDtoRes> getAllFirmasDigitales();
}
