package com.springpath.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();

    public String askDoubt(String topicTitle, String topicDescription,
                           String whyLearn, String question) {

        String prompt = """
                You are SpringPath AI Tutor — a friendly, expert Spring Boot teacher.
                You explain concepts in simple, plain English with real-world analogies.
                You never use unnecessary jargon.
                You always give code examples when relevant.
                Keep answers focused, clear, and practical.
                
                The student is currently studying: %s
                Topic description: %s
                Why they are learning this: %s
                
                Student question: %s
                
                Answer their question specifically in the context of this topic.
                If their question is unrelated to Spring Boot, gently redirect them.
                """.formatted(topicTitle, topicDescription, whyLearn, question);

        return callGeminiApi(prompt);
    }

    public String generateChallenge(String topicTitle, String difficulty) {

        String prompt = """
                You are SpringPath AI Tutor creating a coding challenge.
                Generate a practical, focused coding challenge for: %s
                Difficulty level: %s
                
                Format your response EXACTLY like this:
                CHALLENGE:
                [Clear description of what to implement]
                
                STARTER CODE:
                [Java code with comments showing what to fill in]
                
                HINTS:
                [2-3 helpful hints without giving away the answer]
                
                Keep it practical and directly related to the topic.
                The challenge should take 10-15 minutes to complete.
                """.formatted(topicTitle, difficulty);

        return callGeminiApi(prompt);
    }

    public String reviewCode(String topicTitle, String challenge,
                             String userCode) {

        String prompt = """
                You are SpringPath AI Tutor reviewing student code.
                Topic: %s
                Challenge: %s
                Student's code: %s
                
                Review the student's code submission.
                Be encouraging but honest.
                
                Format your response EXACTLY like this:
                SCORE: [X/10]
                
                WHAT YOU DID WELL:
                [Specific praise for good parts]
                
                WHAT TO IMPROVE:
                [Specific, actionable feedback]
                
                CORRECTED CODE:
                [Show the improved version with comments]
                
                KEY TAKEAWAY:
                [One sentence they should remember]
                """.formatted(topicTitle, challenge, userCode);

        return callGeminiApi(prompt);
    }

    private String callGeminiApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> text = new HashMap<>();
        text.put("text", prompt);

        Map<String, Object> part = new HashMap<>();
        part.put("parts", List.of(text));
        part.put("role", "user");

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(part));

        String urlWithKey = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    urlWithKey, HttpMethod.POST, entity, Map.class);

            List<Map> candidates = (List<Map>) response.getBody()
                    .get("candidates");
            Map content = (Map) candidates.get(0).get("content");
            List<Map> parts = (List<Map>) content.get("parts");
            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            System.out.println("Gemini API ERROR: " + e.getMessage());
            e.printStackTrace();
            return "AI Error: " + e.getMessage();
        }
    }
}