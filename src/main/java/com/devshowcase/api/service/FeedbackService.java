package com.devshowcase.api.service;

import com.devshowcase.api.dto.FeedbackRequestDTO;
import com.devshowcase.api.entity.Feedback;
import com.devshowcase.api.entity.Project;
import com.devshowcase.api.repository.FeedbackRepository;
import com.devshowcase.api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProjectRepository projectRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ProjectRepository projectRepository) {
        this.feedbackRepository = feedbackRepository;
        this.projectRepository = projectRepository;
    }

    public Feedback create(FeedbackRequestDTO dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Feedback feedback = new Feedback();

        feedback.setComment(dto.getComment());
        feedback.setProject(project);

        return feedbackRepository.save(feedback);
    }
}