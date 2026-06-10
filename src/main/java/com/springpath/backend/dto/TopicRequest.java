package com.springpath.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopicRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Why learn is required")
    private String whyLearn;

    @NotBlank(message = "Where used is required")
    private String whereUsed;

    @NotBlank(message = "What breaks is required")
    private String whatBreaks;

    @NotNull(message = "Order index is required")
    private Integer orderIndex;

    @NotBlank(message = "Difficulty is required")
    private String difficulty;
}