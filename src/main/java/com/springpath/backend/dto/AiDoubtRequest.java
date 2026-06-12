package com.springpath.backend.dto;

import lombok.Data;

@Data
public class AiDoubtRequest {
    private Long topicId;
    private String question;
}