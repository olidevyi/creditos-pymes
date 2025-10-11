package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.service.KycRequestServis;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kyc")
public class KycRequestController {
    private final KycRequestServis kycRequestServis;
}
