package com.joydev.rlhf.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joydev.rlhf.dto.request.SftDatasetRequest;
import com.joydev.rlhf.dto.response.SftExportItem;
import com.joydev.rlhf.enums.DatasetStatus;
import com.joydev.rlhf.model.*;
import com.joydev.rlhf.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SftDatasetServiceImpl {

    private final SftDatasetRepository sftDatasetRepository;
    private final PromptRepository promptRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SftDatasetServiceImpl(SftDatasetRepository sftDatasetRepository,
            PromptRepository promptRepository, UserRepository userRepository) {
        this.sftDatasetRepository = sftDatasetRepository;
        this.promptRepository = promptRepository;
        this.userRepository = userRepository;
    }

    public SftDataset submitGoldResponse(SftDatasetRequest request, String username) {
        User annotator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Prompt prompt = promptRepository.findById(request.getPromptId())
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        SftDataset dataset = new SftDataset();
        dataset.setPrompt(prompt);
        dataset.setGoldResponse(request.getGoldResponse());
        dataset.setDomain(request.getDomain());
        dataset.setAnnotator(annotator);
        return sftDatasetRepository.save(dataset);
    }

    public SftDataset reviewDataset(Long id, String reviewerUsername, boolean approve, String notes) {
        User reviewer = userRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        SftDataset dataset = sftDatasetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dataset entry not found"));
        dataset.setReviewer(reviewer);
        dataset.setReviewerNotes(notes);
        dataset.setStatus(approve ? DatasetStatus.APPROVED : DatasetStatus.UNDER_REVIEW);
        dataset.setReviewedAt(LocalDateTime.now());
        return sftDatasetRepository.save(dataset);
    }

    public List<SftDataset> getPendingReviews() {
        return sftDatasetRepository.findByStatus(DatasetStatus.DRAFT);
    }

    public String exportAsJsonl(String domain) {
        List<SftDataset> approved = domain != null
                ? sftDatasetRepository.findByStatusAndDomain(DatasetStatus.APPROVED, domain)
                : sftDatasetRepository.findByStatus(DatasetStatus.APPROVED);
        StringBuilder jsonl = new StringBuilder();
        for (SftDataset entry : approved) {
            try {
                SftExportItem item = new SftExportItem(
                        entry.getPrompt().getContent(),
                        entry.getGoldResponse(),
                        entry.getDomain());
                jsonl.append(objectMapper.writeValueAsString(item)).append("\n");
                entry.setStatus(DatasetStatus.EXPORTED);
                sftDatasetRepository.save(entry);
            } catch (Exception e) {
                throw new RuntimeException("Export failed: " + e.getMessage());
            }
        }
        return jsonl.toString();
    }

    public List<SftDataset> getMySubmissions(String username) {
        User annotator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return sftDatasetRepository.findByAnnotatorId(annotator.getId());
    }
}
