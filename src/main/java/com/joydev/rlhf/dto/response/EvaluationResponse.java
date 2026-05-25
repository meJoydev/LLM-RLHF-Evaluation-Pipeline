package com.joydev.rlhf.dto.response;

import com.joydev.rlhf.enums.EvaluationStatus;
import java.time.LocalDateTime;

public class EvaluationResponse {
    private Long id;
    private Long responseId;
    private String annotatorUsername;
    private Integer relevanceScore;
    private Integer accuracyScore;
    private Integer clarityScore;
    private Integer safetyScore;
    private Integer helpfulnessScore;
    private Double overallScore;
    private String rationale;
    private EvaluationStatus status;
    private LocalDateTime createdAt;

    public EvaluationResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }
    public String getAnnotatorUsername() { return annotatorUsername; }
    public void setAnnotatorUsername(String annotatorUsername) { this.annotatorUsername = annotatorUsername; }
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
