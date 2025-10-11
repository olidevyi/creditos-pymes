package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.KycRequestDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.KycRequestDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.KycRequestRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.KycRequestServis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KycRequestServiceImpl implements KycRequestServis {
    private final KycRequestRepository  kycRequestRepository;

    @Transactional
    @Override
    public KycRequestDtoRes save(KycRequestDtoReq clienteRequestDTO) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<KycRequestDtoRes> listAll() {
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public KycRequestDtoRes findById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public KycRequestDtoRes update(Long id, KycRequestDtoReq clienteRequestDTO) {
        return null;
    }

    @Transactional
    @Override
    public KycRequestDtoRes delete(Long id) {
        return null;
    }
}
