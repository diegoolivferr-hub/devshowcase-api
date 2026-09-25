package com.devshowcase.api.service;

import com.devshowcase.api.dto.FeedbackRequestDTO;
import com.devshowcase.api.entity.Feedback;
import com.devshowcase.api.entity.Project;
import com.devshowcase.api.repository.FeedbackRepository;
import com.devshowcase.api.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Feedback create(Long projectId, FeedbackRequestDTO dto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Feedback feedback = new Feedback();

        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        feedback.setProject(project);

        feedbackRepository.save(feedback);

        List<Feedback> feedbacks =
                feedbackRepository.findByProjectId(projectId);

        double average = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        project.setAverageRating(average);

        projectRepository.save(project);

        return feedback;
    }
}