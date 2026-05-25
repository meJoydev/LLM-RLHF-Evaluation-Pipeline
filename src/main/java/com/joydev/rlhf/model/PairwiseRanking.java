package com.joydev.rlhf.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joydev.rlhf.enums.PreferredResponse;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "pairwise_rankings")
public class PairwiseRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_a_id", nullable = false)
    private LlmResponse responseA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_b_id", nullable = false)
    private LlmResponse responseB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annotator_id", nullable = false)
    private User annotator;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PreferredResponse preferredResponse;

    @Column(columnDefinition = "TEXT")
    private String reasoning;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    public PairwiseRanking() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Prompt getPrompt() { return prompt; }
    public void setPrompt(Prompt prompt) { this.prompt = prompt; }
    public LlmResponse getResponseA() { return responseA; }
    public void setResponseA(LlmResponse responseA) { this.responseA = responseA; }
    public LlmResponse getResponseB() { return responseB; }
    public void setResponseB(LlmResponse responseB) { this.responseB = responseB; }
    public User getAnnotator() { return annotator; }
    public void setAnnotator(User annotator) { this.annotator = annotator; }
    public PreferredResponse getPreferredResponse() { return preferredResponse; }
    public void setPreferredResponse(PreferredResponse preferredResponse) { this.preferredResponse = preferredResponse; }
    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
