package com.joydev.rlhf.dto.request;

import com.joydev.rlhf.enums.PreferredResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PairwiseRankingRequest {
    @NotNull private Long promptId;
    @NotNull private Long responseAId;
    @NotNull private Long responseBId;
    @NotNull private PreferredResponse preferredResponse;
    @NotBlank(message = "Please explain why you preferred this response") private String reasoning;

    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public Long getResponseAId() { return responseAId; }
    public void setResponseAId(Long responseAId) { this.responseAId = responseAId; }
    public Long getResponseBId() { return responseBId; }
    public void setResponseBId(Long responseBId) { this.responseBId = responseBId; }
    public PreferredResponse getPreferredResponse() { return preferredResponse; }
    public void setPreferredResponse(PreferredResponse preferredResponse) { this.preferredResponse = preferredResponse; }
    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }
}
