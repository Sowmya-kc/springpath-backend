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
    private Integer orderIndex;
    private String difficulty;
}