package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;

import java.util.List;

public interface FirmaDigitalServis {
    FirmaDigitalDtoRes save(FirmaDigitalDtoReq firmaDigitalDtoReq);
    List<FirmaDigitalDtoRes> listAll();
    FirmaDigitalDtoRes findById(Long id);
    FirmaDigitalDtoRes update(Long id, FirmaDigitalDtoReq firmaDigitalDtoReq);
    FirmaDigitalDtoRes delete(Long id);
}
