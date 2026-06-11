package com.springpath.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse {

    private Long id;
    private String title;
    private String description;
    private String whyLearn;
    private String whereUsed;
    private String whatBreaks;
    private String realWorldAnalogy;
    private String codeExample;
    private String codeExplanation;
    private String diagramType;
    private String interviewQa;
    private Integer orderIndex;
    private String difficulty;
    private Integer estimatedMinutes;
}