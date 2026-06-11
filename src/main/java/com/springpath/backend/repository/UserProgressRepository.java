package com.springpath.backend.repository;

import com.springpath.backend.model.ProgressStatus;
import com.springpath.backend.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    // Get all progress for a specific user
    List<UserProgress> findByUserId(Long userId);

    // Get progress for a specific user on a specific topic
    Optional<UserProgress> findByUserIdAndTopicId(Long userId, Long topicId);

    // Get all completed topics for a user
    List<UserProgress> findByUserIdAndStatus(Long userId, ProgressStatus status);

    // Count how many topics a user completed
    long countByUserIdAndStatus(Long userId, ProgressStatus status);

    // Check if progress record exists
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
}