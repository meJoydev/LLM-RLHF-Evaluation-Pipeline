package com.joydev.rlhf.repository;

import com.joydev.rlhf.model.LlmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LlmResponseRepository extends JpaRepository<LlmResponse, Long> {
    List<LlmResponse> findByPromptId(Long promptId);

    @Query("SELECT r FROM LlmResponse r ORDER BY r.rewardScore DESC")
    List<LlmResponse> findAllOrderByRewardScoreDesc();
}
