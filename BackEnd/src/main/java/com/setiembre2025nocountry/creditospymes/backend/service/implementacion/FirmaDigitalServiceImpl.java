package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.FirmaDigitalDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.FirmaDigitalDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.FirmaDigitalRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FirmaDigitalServiceImpl implements FirmaDigitalServis {
    private final FirmaDigitalRepository  firmaDigitalRepository;

    @Transactional
    @Override
    public FirmaDigitalDtoRes save(FirmaDigitalDtoReq firmaDigitalDtoReq) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FirmaDigitalDtoRes> listAll() {
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public FirmaDigitalDtoRes findById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public FirmaDigitalDtoRes update(Long id, FirmaDigitalDtoReq firmaDigitalDtoReq) {
        return null;
    }

    @Transactional
    @Override
    public FirmaDigitalDtoRes delete(Long id) {
        return null;
    }
}
