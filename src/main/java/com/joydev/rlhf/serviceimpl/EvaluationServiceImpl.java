package com.joydev.rlhf.serviceimpl;

import com.joydev.rlhf.dto.request.EvaluationRequest;
import com.joydev.rlhf.dto.response.EvaluationResponse;
import com.joydev.rlhf.model.Evaluation;
import com.joydev.rlhf.model.LlmResponse;
import com.joydev.rlhf.model.User;
import com.joydev.rlhf.repository.EvaluationRepository;
import com.joydev.rlhf.repository.LlmResponseRepository;
import com.joydev.rlhf.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl {

    private final EvaluationRepository evaluationRepository;
    private final LlmResponseRepository responseRepository;
    private final UserRepository userRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository,
            LlmResponseRepository responseRepository, UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
    }

    public EvaluationResponse submitEvaluation(EvaluationRequest request, String username) {
        User annotator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LlmResponse llmResponse = responseRepository.findById(request.getResponseId())
                .orElseThrow(() -> new RuntimeException("Response not found with id: " + request.getResponseId()));

        Evaluation evaluation = new Evaluation();
        evaluation.setLlmResponse(llmResponse);
        evaluation.setAnnotator(annotator);
        evaluation.setRelevanceScore(request.getRelevanceScore());
        evaluation.setAccuracyScore(request.getAccuracyScore());
        evaluation.setClarityScore(request.getClarityScore());
        evaluation.setSafetyScore(request.getSafetyScore());
        evaluation.setHelpfulnessScore(request.getHelpfulnessScore());
        evaluation.setRationale(request.getRationale());
        Evaluation saved = evaluationRepository.save(evaluation);

        Double avgScore = evaluationRepository.findAverageScoreByResponseId(llmResponse.getId());
        llmResponse.setRewardScore(avgScore);
        responseRepository.save(llmResponse);

        return mapToResponse(saved);
    }

    public List<EvaluationResponse> getEvaluationsByResponse(Long responseId) {
        return evaluationRepository.findByLlmResponseId(responseId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<EvaluationResponse> getMyEvaluations(String username) {
        User annotator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return evaluationRepository.findByAnnotatorId(annotator.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private EvaluationResponse mapToResponse(Evaluation e) {
        EvaluationResponse r = new EvaluationResponse();
        r.setId(e.getId());
        r.setResponseId(e.getLlmResponse().getId());
        r.setAnnotatorUsername(e.getAnnotator().getUsername());
        r.setRelevanceScore(e.getRelevanceScore());
        r.setAccuracyScore(e.getAccuracyScore());
        r.setClarityScore(e.getClarityScore());
        r.setSafetyScore(e.getSafetyScore());
        r.setHelpfulnessScore(e.getHelpfulnessScore());
        r.setOverallScore(e.getOverallScore());
        r.setRationale(e.getRationale());
        r.setStatus(e.getStatus());
        r.setCreatedAt(e.getCreatedAt());
        return r;
    }
}
