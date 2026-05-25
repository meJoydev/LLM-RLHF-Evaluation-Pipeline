package com.joydev.rlhf.controller;

import com.joydev.rlhf.dto.request.PairwiseRankingRequest;
import com.joydev.rlhf.dto.response.ApiResponse;
import com.joydev.rlhf.serviceimpl.PairwiseRankingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/rankings")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Pairwise Ranking (RLHF)", description = "Compare two AI responses for reward modeling")
public class PairwiseRankingController {

    private final PairwiseRankingServiceImpl rankingService;

    public PairwiseRankingController(PairwiseRankingServiceImpl rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping
    @Operation(summary = "Submit a pairwise comparison")
    public ResponseEntity<ApiResponse<?>> submitRanking(@Valid @RequestBody PairwiseRankingRequest request, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Ranking submitted",
                rankingService.submitRanking(request, principal.getName())));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get reward score leaderboard")
    public ResponseEntity<ApiResponse<?>> getLeaderboard() {
        return ResponseEntity.ok(ApiResponse.success("Leaderboard", rankingService.getLeaderboard()));
    }
}
