package com.devshowcase.api.controller;

import com.devshowcase.api.dto.TechnologyRequestDTO;
import com.devshowcase.api.dto.TechnologyResponseDTO;
import com.devshowcase.api.service.TechnologyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnologyResponseDTO create(
            @Valid @RequestBody TechnologyRequestDTO dto) {

        return technologyService.create(dto);
    }

    @GetMapping
    public List<TechnologyResponseDTO> findAll() {
        return technologyService.findAll();
    }
}