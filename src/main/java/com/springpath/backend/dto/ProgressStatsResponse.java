package com.springpath.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressStatsResponse {
    private long completedTopics;
    private long inProgressTopics;
    private long totalTopics;
}