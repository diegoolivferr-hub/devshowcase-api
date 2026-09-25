package com.devshowcase.api.controller;

import com.devshowcase.api.dto.ProjectRequestDTO;
import com.devshowcase.api.dto.ProjectResponseDTO;
import com.devshowcase.api.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDTO create(
            @Valid @RequestBody ProjectRequestDTO dto) {

        return projectService.create(dto);
    }

   @GetMapping
public Page<ProjectResponseDTO> findAll(
        @RequestParam(required = false) Long technologyId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    return projectService.findAll(technologyId, page, size);
}

    @PutMapping("/{id}/upvote")
public ProjectResponseDTO upvote(@PathVariable Long id) {
    return projectService.upvote(id);
}
}