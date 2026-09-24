package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProfileRequestDTO;
import com.devshowcase.api.dto.ProfileResponseDTO;
import com.devshowcase.api.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDTO create(@Valid @RequestBody ProfileRequestDTO dto) {
        return profileService.create(dto);
    }

    @GetMapping("/{id}")
    public ProfileResponseDTO findById(@PathVariable Long id) {
        return profileService.findById(id);
    }
}