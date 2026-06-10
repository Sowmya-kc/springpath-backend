package com.springpath.backend.controller;

import com.springpath.backend.dto.TopicRequest;
import com.springpath.backend.dto.TopicResponse;
import com.springpath.backend.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "*")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // ADMIN — create a topic
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(
            @Valid @RequestBody TopicRequest request) {
        TopicResponse response = topicService.createTopic(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ALL USERS — get full roadmap
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    // ALL USERS — get single topic
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    // ALL USERS — get topics by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<TopicResponse>> getByDifficulty(
            @PathVariable String difficulty) {
        return ResponseEntity.ok(topicService.getTopicsByDifficulty(difficulty));
    }

    // ADMIN — update a topic
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(
            @PathVariable Long id,
            @Valid @RequestBody TopicRequest request) {
        return ResponseEntity.ok(topicService.updateTopic(id, request));
    }

    // ADMIN — delete a topic
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.deleteTopic(id));
    }
}