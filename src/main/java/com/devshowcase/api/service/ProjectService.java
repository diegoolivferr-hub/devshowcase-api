package com.devshowcase.api.service;

import com.devshowcase.api.dto.ProjectRequestDTO;
import com.devshowcase.api.dto.ProjectResponseDTO;
import com.devshowcase.api.entity.Profile;
import com.devshowcase.api.entity.Project;
import com.devshowcase.api.entity.Technology;
import com.devshowcase.api.repository.ProfileRepository;
import com.devshowcase.api.repository.ProjectRepository;
import com.devshowcase.api.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            ProfileRepository profileRepository,
            TechnologyRepository technologyRepository) {

        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.technologyRepository = technologyRepository;
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {

        Profile profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Project project = new Project();

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setUrl(dto.getUrl());
        project.setProfile(profile);

        if (dto.getTechnologyIds() != null) {
            List<Technology> technologies =
                    technologyRepository.findAllById(dto.getTechnologyIds());

            project.setTechnologies(technologies);
        }

        Project savedProject = projectRepository.save(project);

        return convertToDTO(savedProject);
    }

   public Page<ProjectResponseDTO> findAll(
        Long technologyId,
        int page,
        int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<Project> projects;

    if (technologyId != null) {
        projects = projectRepository.findByTechnologiesId(
                technologyId,
                pageable
        );
    } else {
        projects = projectRepository.findAll(pageable);
    }

    return projects.map(this::convertToDTO);
}

    private ProjectResponseDTO convertToDTO(Project project) {

        List<Long> technologyIds = project.getTechnologies()
        .stream()
        .map(technology -> technology.getId())
        .toList();

       return new ProjectResponseDTO(
        project.getId(),
        project.getTitle(),
        project.getDescription(),
        project.getUrl(),
        project.getProfile().getId(),
        technologyIds,
        project.getAverageRating(),
        project.getUpvotes()
);
    }

    public ProjectResponseDTO upvote(Long id) {

    Project project = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

    project.setUpvotes(project.getUpvotes() + 1);

    Project updatedProject = projectRepository.save(project);

    return convertToDTO(updatedProject);
}
}