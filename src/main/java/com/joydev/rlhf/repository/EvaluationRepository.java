package com.joydev.rlhf.repository;

import com.joydev.rlhf.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByAnnotatorId(Long annotatorId);
    List<Evaluation> findByLlmResponseId(Long responseId);

    @Query("SELECT AVG(e.overallScore) FROM Evaluation e WHERE e.llmResponse.id = :responseId")
    Double findAverageScoreByResponseId(Long responseId);
}
