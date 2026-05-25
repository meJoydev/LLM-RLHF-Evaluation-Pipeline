package com.joydev.rlhf.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joydev.rlhf.enums.EvaluationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private LlmResponse llmResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annotator_id", nullable = false)
    private User annotator;

    @Column(nullable = false)
    private Integer relevanceScore;

    @Column(nullable = false)
    private Integer accuracyScore;

    @Column(nullable = false)
    private Integer clarityScore;

    @Column(nullable = false)
    private Integer safetyScore;

    @Column(nullable = false)
    private Integer helpfulnessScore;

    @Column(name = "overall_score")
    private Double overallScore;

    @Column(columnDefinition = "TEXT")
    private String rationale;

    @Enumerated(EnumType.STRING)
    private EvaluationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.overallScore = (relevanceScore + accuracyScore + clarityScore + safetyScore + helpfulnessScore) / 5.0;
        this.status = EvaluationStatus.COMPLETED;
    }

    public Evaluation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LlmResponse getLlmResponse() { return llmResponse; }
    public void setLlmResponse(LlmResponse llmResponse) { this.llmResponse = llmResponse; }
    public User getAnnotator() { return annotator; }
    public void setAnnotator(User annotator) { this.annotator = annotator; }
    public Integer getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(Integer relevanceScore) { this.relevanceScore = relevanceScore; }
    public Integer getAccuracyScore() { return accuracyScore; }
    public void setAccuracyScore(Integer accuracyScore) { this.accuracyScore = accuracyScore; }
    public Integer getClarityScore() { return clarityScore; }
    public void setClarityScore(Integer clarityScore) { this.clarityScore = clarityScore; }
    public Integer getSafetyScore() { return safetyScore; }
    public void setSafetyScore(Integer safetyScore) { this.safetyScore = safetyScore; }
    public Integer getHelpfulnessScore() { return helpfulnessScore; }
    public void setHelpfulnessScore(Integer helpfulnessScore) { this.helpfulnessScore = helpfulnessScore; }
    public Double getOverallScore() { return overallScore; }
    public void setOverallScore(Double overallScore) { this.overallScore = overallScore; }
    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }
    public EvaluationStatus getStatus() { return status; }
    public void setStatus(EvaluationStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
