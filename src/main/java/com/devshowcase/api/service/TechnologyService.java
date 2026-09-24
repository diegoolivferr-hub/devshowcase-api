package com.devshowcase.api.service;

import com.devshowcase.api.dto.TechnologyRequestDTO;
import com.devshowcase.api.dto.TechnologyResponseDTO;
import com.devshowcase.api.entity.Technology;
import com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public TechnologyResponseDTO create(TechnologyRequestDTO dto) {

        Technology technology = new Technology();

        technology.setName(dto.getName());

        Technology savedTechnology = technologyRepository.save(technology);

        return new TechnologyResponseDTO(
                savedTechnology.getId(),
                savedTechnology.getName()
        );
    }

    public List<TechnologyResponseDTO> findAll() {

        return technologyRepository.findAll()
                .stream()
                .map(technology -> new TechnologyResponseDTO(
                        technology.getId(),
                        technology.getName()
                ))
                .toList();
    }
}