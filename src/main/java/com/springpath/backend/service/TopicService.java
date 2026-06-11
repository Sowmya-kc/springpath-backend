package com.springpath.backend.service;

import com.springpath.backend.dto.TopicRequest;
import com.springpath.backend.dto.TopicResponse;
import com.springpath.backend.model.Topic;
import com.springpath.backend.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // Create a new topic (admin only)
    public TopicResponse createTopic(TopicRequest request) {

        if (topicRepository.existsByOrderIndex(request.getOrderIndex())) {
            throw new RuntimeException("A topic with this order position already exists");
        }

        Topic topic = new Topic();
        topic.setTitle(request.getTitle());
        topic.setDescription(request.getDescription());
        topic.setWhyLearn(request.getWhyLearn());
        topic.setWhereUsed(request.getWhereUsed());
        topic.setWhatBreaks(request.getWhatBreaks());
        topic.setOrderIndex(request.getOrderIndex());
        topic.setDifficulty(request.getDifficulty());

        Topic saved = topicRepository.save(topic);
        return mapToResponse(saved);
    }

    // Get all topics in roadmap order
    public List<TopicResponse> getAllTopics() {
        return topicRepository.findAllByOrderByOrderIndexAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get single topic by id
    public TopicResponse getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        return mapToResponse(topic);
    }

    // Get topics by difficulty
    public List<TopicResponse> getTopicsByDifficulty(String difficulty) {
        return topicRepository.findByDifficultyOrderByOrderIndexAsc(difficulty)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Update a topic (admin only)
    public TopicResponse updateTopic(Long id, TopicRequest request) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));

        topic.setTitle(request.getTitle());
        topic.setDescription(request.getDescription());
        topic.setWhyLearn(request.getWhyLearn());
        topic.setWhereUsed(request.getWhereUsed());
        topic.setWhatBreaks(request.getWhatBreaks());
        topic.setOrderIndex(request.getOrderIndex());
        topic.setDifficulty(request.getDifficulty());
        topic.setRealWorldAnalogy(request.getRealWorldAnalogy());
        topic.setCodeExample(request.getCodeExample());
        topic.setCodeExplanation(request.getCodeExplanation());
        topic.setDiagramType(request.getDiagramType());
        topic.setInterviewQa(request.getInterviewQa());
        topic.setEstimatedMinutes(request.getEstimatedMinutes());

        Topic updated = topicRepository.save(topic);
        return mapToResponse(updated);
    }

    // Delete a topic (admin only)
    public String deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        topicRepository.delete(topic);
        return "Topic deleted successfully";
    }

    // Convert Topic entity to TopicResponse DTO
    private TopicResponse mapToResponse(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getDescription(),
                topic.getWhyLearn(),
                topic.getWhereUsed(),
                topic.getWhatBreaks(),
                topic.getRealWorldAnalogy(),
                topic.getCodeExample(),
                topic.getCodeExplanation(),
                topic.getDiagramType(),
                topic.getInterviewQa(),
                topic.getOrderIndex(),
                topic.getDifficulty(),
                topic.getEstimatedMinutes()
        );
    }
}