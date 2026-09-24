package com.devshowcase.api.controller;

import com.devshowcase.api.dto.FeedbackRequestDTO;
import com.devshowcase.api.entity.Feedback;
import com.devshowcase.api.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback create(@Valid @RequestBody FeedbackRequestDTO dto) {
        return feedbackService.create(dto);
    }
}