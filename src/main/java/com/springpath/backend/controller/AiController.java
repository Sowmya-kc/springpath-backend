package com.springpath.backend.controller;

import com.springpath.backend.dto.AiChallengeRequest;
import com.springpath.backend.dto.AiDoubtRequest;
import com.springpath.backend.dto.AiReviewRequest;
import com.springpath.backend.model.Topic;
import com.springpath.backend.repository.TopicRepository;
import com.springpath.backend.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    @Autowired
    private AiService aiService;

    @Autowired
    private TopicRepository topicRepository;

    // Ask AI a doubt about a topic
    @PostMapping("/doubt")
    public ResponseEntity<Map<String, String>> askDoubt(
            @RequestBody AiDoubtRequest request) {

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        String answer = aiService.askDoubt(
                topic.getTitle(),
                topic.getDescription(),
                topic.getWhyLearn(),
                request.getQuestion()
        );

        return ResponseEntity.ok(Map.of("answer", answer));
    }

    // Generate coding challenge for a topic
    @PostMapping("/challenge")
    public ResponseEntity<Map<String, String>> generateChallenge(
            @RequestBody AiChallengeRequest request) {

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        String challenge = aiService.generateChallenge(
                topic.getTitle(),
                topic.getDifficulty()
        );

        return ResponseEntity.ok(Map.of("challenge", challenge));
    }

    // Review submitted code
    @PostMapping("/review")
    public ResponseEntity<Map<String, String>> reviewCode(
            @RequestBody AiReviewRequest request) {

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        String review = aiService.reviewCode(
                topic.getTitle(),
                request.getChallenge(),
                request.getUserCode()
        );

        return ResponseEntity.ok(Map.of("review", review));
    }
}