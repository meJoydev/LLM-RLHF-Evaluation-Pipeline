package com.joydev.rlhf.controller;

import com.joydev.rlhf.dto.request.EvaluationRequest;
import com.joydev.rlhf.dto.response.ApiResponse;
import com.joydev.rlhf.serviceimpl.EvaluationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/evaluations")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "5-Axis Evaluation", description = "Rate AI responses on relevance, accuracy, clarity, safety, helpfulness")
public class EvaluationController {

    private final EvaluationServiceImpl evaluationService;

    public EvaluationController(EvaluationServiceImpl evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    @Operation(summary = "Submit a 5-axis evaluation")
    public ResponseEntity<ApiResponse<?>> submitEvaluation(@Valid @RequestBody EvaluationRequest request, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Evaluation submitted",
                evaluationService.submitEvaluation(request, principal.getName())));
    }

    @GetMapping("/response/{responseId}")
    @Operation(summary = "Get all evaluations for a response")
    public ResponseEntity<ApiResponse<?>> getByResponse(@PathVariable Long responseId) {
        return ResponseEntity.ok(ApiResponse.success("Evaluations retrieved",
                evaluationService.getEvaluationsByResponse(responseId)));
    }

    @GetMapping("/my")
    @Operation(summary = "Get my evaluations")
    public ResponseEntity<ApiResponse<?>> getMyEvaluations(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Your evaluations",
                evaluationService.getMyEvaluations(principal.getName())));
    }
}
