package com.joydev.rlhf.serviceimpl;

import com.joydev.rlhf.dto.request.PairwiseRankingRequest;
import com.joydev.rlhf.dto.response.RewardScoreResponse;
import com.joydev.rlhf.enums.PreferredResponse;
import com.joydev.rlhf.model.*;
import com.joydev.rlhf.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PairwiseRankingServiceImpl {

    private final PairwiseRankingRepository rankingRepository;
    private final LlmResponseRepository responseRepository;
    private final PromptRepository promptRepository;
    private final UserRepository userRepository;

    public PairwiseRankingServiceImpl(PairwiseRankingRepository rankingRepository,
            LlmResponseRepository responseRepository, PromptRepository promptRepository,
            UserRepository userRepository) {
        this.rankingRepository = rankingRepository;
        this.responseRepository = responseRepository;
        this.promptRepository = promptRepository;
        this.userRepository = userRepository;
    }

    public String submitRanking(PairwiseRankingRequest request, String username) {
        User annotator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Prompt prompt = promptRepository.findById(request.getPromptId())
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        LlmResponse responseA = responseRepository.findById(request.getResponseAId())
                .orElseThrow(() -> new RuntimeException("Response A not found"));
        LlmResponse responseB = responseRepository.findById(request.getResponseBId())
                .orElseThrow(() -> new RuntimeException("Response B not found"));

        PairwiseRanking ranking = new PairwiseRanking();
        ranking.setPrompt(prompt);
        ranking.setResponseA(responseA);
        ranking.setResponseB(responseB);
        ranking.setAnnotator(annotator);
        ranking.setPreferredResponse(request.getPreferredResponse());
        ranking.setReasoning(request.getReasoning());
        rankingRepository.save(ranking);
        updateRewardScores(responseA, responseB, request.getPreferredResponse());
        return "Pairwise ranking submitted successfully";
    }

    private void updateRewardScores(LlmResponse a, LlmResponse b, PreferredResponse preferred) {
        if (preferred == PreferredResponse.RESPONSE_A) {
            a.setRewardScore(a.getRewardScore() + 1.0);
        } else if (preferred == PreferredResponse.RESPONSE_B) {
            b.setRewardScore(b.getRewardScore() + 1.0);
        } else {
            a.setRewardScore(a.getRewardScore() + 0.5);
            b.setRewardScore(b.getRewardScore() + 0.5);
        }
        responseRepository.save(a);
        responseRepository.save(b);
    }

    public List<RewardScoreResponse> getLeaderboard() {
        return responseRepository.findAllOrderByRewardScoreDesc()
                .stream().map(r -> {
                    Long winsA = rankingRepository.countWinsForResponseA(r.getId(), PreferredResponse.RESPONSE_A);
                    Long winsB = rankingRepository.countWinsForResponseB(r.getId(), PreferredResponse.RESPONSE_B);
                    Long totalWins = winsA + winsB;
                    List<PairwiseRanking> comparisons = rankingRepository.findByPromptId(r.getPrompt().getId());
                    long total = comparisons.size();
                    double winRate = total > 0 ? (totalWins * 100.0 / total) : 0.0;
                    return new RewardScoreResponse(r.getId(), r.getModelName(), r.getRewardScore(),
                            totalWins, total, winRate, r.getPrompt().getContent());
                }).collect(Collectors.toList());
    }
}
