package com.springpath.backend.repository;

import com.springpath.backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    // Get all topics ordered by their position in roadmap
    List<Topic> findAllByOrderByOrderIndexAsc();

    // Get topics by difficulty level
    List<Topic> findByDifficultyOrderByOrderIndexAsc(String difficulty);

    // Check if a topic order position is already taken
    boolean existsByOrderIndex(Integer orderIndex);
}