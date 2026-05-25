package com.joydev.rlhf.controller;

import com.joydev.rlhf.dto.request.LlmResponseRequest;
import com.joydev.rlhf.dto.request.PromptRequest;
import com.joydev.rlhf.dto.response.ApiResponse;
import com.joydev.rlhf.serviceimpl.PromptServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/prompts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Prompts & Responses", description = "Manage prompts and AI model responses")
public class PromptController {

    private final PromptServiceImpl promptService;

    public PromptController(PromptServiceImpl promptService) {
        this.promptService = promptService;
    }

    @PostMapping
    @Operation(summary = "Create a new prompt")
    public ResponseEntity<ApiResponse<?>> createPrompt(@Valid @RequestBody PromptRequest request, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Prompt created", promptService.createPrompt(request, principal.getName())));
    }

    @GetMapping
    @Operation(summary = "Get all prompts")
    public ResponseEntity<ApiResponse<?>> getAllPrompts() {
        return ResponseEntity.ok(ApiResponse.success("Prompts retrieved", promptService.getAllPrompts()));
    }

    @GetMapping("/domain/{domain}")
    @Operation(summary = "Get prompts by domain")
    public ResponseEntity<ApiResponse<?>> getByDomain(@PathVariable String domain) {
        return ResponseEntity.ok(ApiResponse.success("Prompts retrieved", promptService.getPromptsByDomain(domain)));
    }

    @PostMapping("/responses")
    @Operation(summary = "Add an AI response to a prompt")
    public ResponseEntity<ApiResponse<?>> addResponse(@Valid @RequestBody LlmResponseRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Response added", promptService.addResponse(request)));
    }

    @GetMapping("/{promptId}/responses")
    @Operation(summary = "Get all responses for a prompt")
    public ResponseEntity<ApiResponse<?>> getResponses(@PathVariable Long promptId) {
        return ResponseEntity.ok(ApiResponse.success("Responses retrieved", promptService.getResponsesByPrompt(promptId)));
    }
}
