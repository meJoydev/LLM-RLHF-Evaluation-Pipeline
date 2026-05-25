package com.joydev.rlhf.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PromptRequest {
    @NotBlank(message = "Prompt content is required")
    private String content;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Difficulty is required")
    private String difficulty;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}
