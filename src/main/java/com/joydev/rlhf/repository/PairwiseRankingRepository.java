package com.joydev.rlhf.repository;

import com.joydev.rlhf.enums.PreferredResponse;
import com.joydev.rlhf.model.PairwiseRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PairwiseRankingRepository extends JpaRepository<PairwiseRanking, Long> {
    List<PairwiseRanking> findByPromptId(Long promptId);

    @Query("SELECT COUNT(p) FROM PairwiseRanking p WHERE p.responseA.id = :responseId AND p.preferredResponse = :pref")
    Long countWinsForResponseA(Long responseId, PreferredResponse pref);

    @Query("SELECT COUNT(p) FROM PairwiseRanking p WHERE p.responseB.id = :responseId AND p.preferredResponse = :pref")
    Long countWinsForResponseB(Long responseId, PreferredResponse pref);
}
