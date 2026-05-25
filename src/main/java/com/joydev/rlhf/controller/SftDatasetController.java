package com.joydev.rlhf.controller;

import com.joydev.rlhf.dto.request.SftDatasetRequest;
import com.joydev.rlhf.dto.response.ApiResponse;
import com.joydev.rlhf.serviceimpl.SftDatasetServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/sft")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "SFT Dataset Builder", description = "Submit and export gold-standard training data")
public class SftDatasetController {

    private final SftDatasetServiceImpl sftService;

    public SftDatasetController(SftDatasetServiceImpl sftService) {
        this.sftService = sftService;
    }

    @PostMapping
    @Operation(summary = "Submit a gold-standard response for SFT")
    public ResponseEntity<ApiResponse<?>> submit(@Valid @RequestBody SftDatasetRequest request, Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Gold response submitted",
                sftService.submitGoldResponse(request, principal.getName())));
    }

    @GetMapping("/my")
    @Operation(summary = "View your SFT submissions")
    public ResponseEntity<ApiResponse<?>> getMySubmissions(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Submissions retrieved",
                sftService.getMySubmissions(principal.getName())));
    }

    @GetMapping("/review/pending")
    @Operation(summary = "REVIEWER: View pending SFT entries")
    public ResponseEntity<ApiResponse<?>> getPending() {
        return ResponseEntity.ok(ApiResponse.success("Pending reviews", sftService.getPendingReviews()));
    }

    @PutMapping("/review/{id}")
    @Operation(summary = "REVIEWER: Approve or reject an SFT entry")
    public ResponseEntity<ApiResponse<?>> review(@PathVariable Long id,
            @RequestParam boolean approve,
            @RequestParam(required = false, defaultValue = "") String notes,
            Principal principal) {
        return ResponseEntity.ok(ApiResponse.success("Review submitted",
                sftService.reviewDataset(id, principal.getName(), approve, notes)));
    }

    @GetMapping("/export")
    @Operation(summary = "REVIEWER: Export approved SFT data as JSONL")
    public ResponseEntity<byte[]> export(@RequestParam(required = false) String domain) {
        String jsonl = sftService.exportAsJsonl(domain);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("sft_dataset.jsonl").build());
        return ResponseEntity.ok().headers(headers).body(jsonl.getBytes());
    }
}
