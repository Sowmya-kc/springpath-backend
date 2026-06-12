package com.springpath.backend.dto;

import lombok.Data;

@Data
public class AiReviewRequest {
    private Long topicId;
    private String challenge;
    private String userCode;
}