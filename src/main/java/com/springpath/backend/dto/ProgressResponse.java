package com.springpath.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long topicId;
    private String topicTitle;
    private String status;
    private String completedAt;
}