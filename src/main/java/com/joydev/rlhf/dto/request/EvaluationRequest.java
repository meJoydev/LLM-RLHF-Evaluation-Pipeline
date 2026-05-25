package com.joydev.rlhf.dto.request;

import jakarta.validation.constraints.*;

public class EvaluationRequest {
    @NotNull private Long responseId;
    @NotNull @Min(1) @Max(5) private Integer relevanceScore;
    @NotNull @Min(1) @Max(5) private Integer accuracyScore;
    @NotNull @Min(1) @Max(5) private Integer clarityScore;
    @NotNull @Min(1) @Max(5) private Integer safetyScore;
    @NotNull @Min(1) @Max(5) private Integer helpfulnessScore;
    @NotBlank(message = "Rationale is required") private String rationale;

    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }
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
    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }
}
