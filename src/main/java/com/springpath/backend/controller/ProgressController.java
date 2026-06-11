package com.springpath.backend.controller;

import com.springpath.backend.dto.ProgressRequest;
import com.springpath.backend.dto.ProgressResponse;
import com.springpath.backend.dto.ProgressStatsResponse;
import com.springpath.backend.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    // Update or create progress for logged in user
    @PostMapping
    public ResponseEntity<ProgressResponse> updateProgress(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProgressRequest request) {
        ProgressResponse response = progressService
                .updateProgress(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    // Get all progress for logged in user
    @GetMapping
    public ResponseEntity<List<ProgressResponse>> getUserProgress(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ProgressResponse> response = progressService
                .getUserProgress(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // Get progress stats for logged in user
    @GetMapping("/stats")
    public ResponseEntity<ProgressStatsResponse> getUserStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        ProgressStatsResponse response = progressService
                .getUserStats(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}