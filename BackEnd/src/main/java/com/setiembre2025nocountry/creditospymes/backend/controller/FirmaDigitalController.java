package com.setiembre2025nocountry.creditospymes.backend.controller;

import com.setiembre2025nocountry.creditospymes.backend.service.FirmaDigitalServis;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/firmas")
public class FirmaDigitalController {
    private final FirmaDigitalServis firmaDigitalServis;
}
