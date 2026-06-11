package com.springpath.backend.service;

import com.springpath.backend.dto.ProgressRequest;
import com.springpath.backend.dto.ProgressResponse;
import com.springpath.backend.dto.ProgressStatsResponse;
import com.springpath.backend.model.*;
import com.springpath.backend.repository.TopicRepository;
import com.springpath.backend.repository.UserProgressRepository;
import com.springpath.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    private UserProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    // Update or create progress for a user on a topic
    public ProgressResponse updateProgress(String email, ProgressRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        ProgressStatus status = ProgressStatus.valueOf(request.getStatus().toUpperCase());

        // Check if progress already exists
        UserProgress progress = progressRepository
                .findByUserIdAndTopicId(user.getId(), topic.getId())
                .orElse(new UserProgress());

        progress.setUser(user);
        progress.setTopic(topic);
        progress.setStatus(status);

        // Set completed time if status is COMPLETED
        if (status == ProgressStatus.COMPLETED) {
            progress.setCompletedAt(LocalDateTime.now());
        } else {
            progress.setCompletedAt(null);
        }

        UserProgress saved = progressRepository.save(progress);
        return mapToResponse(saved);
    }

    // Get all progress for logged in user
    public List<ProgressResponse> getUserProgress(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return progressRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get progress stats for logged in user
    public ProgressStatsResponse getUserStats(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long completed = progressRepository
                .countByUserIdAndStatus(user.getId(), ProgressStatus.COMPLETED);

        long inProgress = progressRepository
                .countByUserIdAndStatus(user.getId(), ProgressStatus.IN_PROGRESS);

        long totalTopics = topicRepository.count();

        return new ProgressStatsResponse(completed, inProgress, totalTopics);
    }

    private ProgressResponse mapToResponse(UserProgress progress) {
        return new ProgressResponse(
                progress.getId(),
                progress.getUser().getId(),
                progress.getUser().getName(),
                progress.getTopic().getId(),
                progress.getTopic().getTitle(),
                progress.getStatus().name(),
                progress.getCompletedAt() != null ?
                        progress.getCompletedAt().toString() : null
        );
    }
}