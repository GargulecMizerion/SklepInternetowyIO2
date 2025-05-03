package com.example.SklepInternetowyIO2.service;

import com.example.SklepInternetowyIO2.model.assortment.Color;
import com.example.SklepInternetowyIO2.repository.assortment.ColorRepository;
import com.example.SklepInternetowyIO2.request.ColorRequest;
import com.example.SklepInternetowyIO2.response.ColorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<ColorResponse> getAllColors() {
        List<Color> allColors = colorRepository.findAll();
        return allColors.stream().map(this::getColorResponse).collect(Collectors.toList());
    }

    public ColorResponse getColor(Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Color with id %s not found", id)));
        return getColorResponse(color);
    }

    public ColorResponse addColor(ColorRequest colorRequest) {
        Color color = new Color();
        color.setColor(colorRequest.getColor());
        colorRepository.save(color);
        return getColorResponse(color);
    }

    private ColorResponse getColorResponse(Color color) {
        return new ColorResponse(color.getId(), color.getColor());
    }
}
