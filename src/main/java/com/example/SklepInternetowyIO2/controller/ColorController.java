package com.example.SklepInternetowyIO2.controller;

import com.example.SklepInternetowyIO2.request.ColorRequest;
import com.example.SklepInternetowyIO2.response.ColorResponse;
import com.example.SklepInternetowyIO2.service.ColorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@Tag(name = "Kolor", description = "Operacje na kolorach produktów")
public class ColorController {

    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public List<ColorResponse> getAllColors() {
        return colorService.getAllColors();
    }

    @GetMapping("/{id}")
    public ColorResponse getColor(@PathVariable Long id) {
        return colorService.getColor(id);
    }

    @PostMapping
    public ColorResponse addColor(@RequestBody ColorRequest colorRequest) {
        return colorService.addColor(colorRequest);
    }
}
