package com.joydev.rlhf.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SftDatasetRequest {
    @NotNull(message = "Prompt ID is required") private Long promptId;
    @NotBlank(message = "Gold response is required") private String goldResponse;
    @NotBlank(message = "Domain is required") private String domain;

    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getGoldResponse() { return goldResponse; }
    public void setGoldResponse(String goldResponse) { this.goldResponse = goldResponse; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
}
