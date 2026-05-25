package com.joydev.rlhf.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LlmResponseRequest {
    @NotNull(message = "Prompt ID is required")
    private Long promptId;

    @NotBlank(message = "Response content is required")
    private String content;

    @NotBlank(message = "Model name is required")
    private String modelName;

    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
}
