package com.joydev.rlhf.dto.response;

public class RewardScoreResponse {
    private Long responseId;
    private String modelName;
    private Double rewardScore;
    private Long totalWins;
    private Long totalComparisons;
    private Double winRate;
    private String promptContent;

    public RewardScoreResponse() {}
    public RewardScoreResponse(Long responseId, String modelName, Double rewardScore,
            Long totalWins, Long totalComparisons, Double winRate, String promptContent) {
        this.responseId = responseId; this.modelName = modelName;
        this.rewardScore = rewardScore; this.totalWins = totalWins;
        this.totalComparisons = totalComparisons; this.winRate = winRate;
        this.promptContent = promptContent;
    }

    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public Double getRewardScore() { return rewardScore; }
    public void setRewardScore(Double rewardScore) { this.rewardScore = rewardScore; }
    public Long getTotalWins() { return totalWins; }
    public void setTotalWins(Long totalWins) { this.totalWins = totalWins; }
    public Long getTotalComparisons() { return totalComparisons; }
    public void setTotalComparisons(Long totalComparisons) { this.totalComparisons = totalComparisons; }
    public Double getWinRate() { return winRate; }
    public void setWinRate(Double winRate) { this.winRate = winRate; }
    public String getPromptContent() { return promptContent; }
    public void setPromptContent(String promptContent) { this.promptContent = promptContent; }
}
