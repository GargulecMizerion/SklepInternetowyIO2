package com.example.SklepInternetowyIO2.controller;

import com.example.SklepInternetowyIO2.request.SizeRequest;
import com.example.SklepInternetowyIO2.response.SizeResponse;
import com.example.SklepInternetowyIO2.service.SizeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")
@Tag(name = "Rozmiar", description = "Operacje na rozmiarach produktów")
public class SizeController {

    private final SizeService sizeService;

    @Autowired
    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public List<SizeResponse> getAllSizes() {
        return sizeService.getAllSizes();
    }

    @GetMapping("/{id}")
    public SizeResponse getSize(@PathVariable Long id) {
        return sizeService.getSize(id);
    }

    @PostMapping
    public SizeResponse addSize(@RequestBody SizeRequest request) {
        return sizeService.addSize(request);
    }


    @DeleteMapping("/{id}")
    public void deleteSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
    }
}

