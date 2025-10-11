package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.KycRequestDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.KycRequestDtoReq;

import java.util.List;

public interface KycRequestServis {
    KycRequestDtoRes save(KycRequestDtoReq clienteRequestDTO);
    List<KycRequestDtoRes> listAll();
    KycRequestDtoRes findById(Long id);
    KycRequestDtoRes update(Long id, KycRequestDtoReq clienteRequestDTO);
    KycRequestDtoRes delete(Long id);
}
