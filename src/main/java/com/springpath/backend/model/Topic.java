package com.springpath.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "why_learn", columnDefinition = "TEXT")
    private String whyLearn;

    @Column(name = "where_used", columnDefinition = "TEXT")
    private String whereUsed;

    @Column(name = "what_breaks", columnDefinition = "TEXT")
    private String whatBreaks;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private String difficulty;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}