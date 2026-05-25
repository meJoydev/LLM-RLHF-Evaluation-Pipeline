package com.joydev.rlhf.serviceimpl;

import com.joydev.rlhf.dto.request.LlmResponseRequest;
import com.joydev.rlhf.dto.request.PromptRequest;
import com.joydev.rlhf.model.LlmResponse;
import com.joydev.rlhf.model.Prompt;
import com.joydev.rlhf.model.User;
import com.joydev.rlhf.repository.LlmResponseRepository;
import com.joydev.rlhf.repository.PromptRepository;
import com.joydev.rlhf.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromptServiceImpl {

    private final PromptRepository promptRepository;
    private final LlmResponseRepository responseRepository;
    private final UserRepository userRepository;

    public PromptServiceImpl(PromptRepository promptRepository,
            LlmResponseRepository responseRepository, UserRepository userRepository) {
        this.promptRepository = promptRepository;
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
    }

    public Prompt createPrompt(PromptRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Prompt prompt = new Prompt();
        prompt.setContent(request.getContent());
        prompt.setDomain(request.getDomain());
        prompt.setDifficulty(request.getDifficulty());
        prompt.setCreatedBy(user);
        return promptRepository.save(prompt);
    }

    public List<Prompt> getAllPrompts() { return promptRepository.findAll(); }

    public List<Prompt> getPromptsByDomain(String domain) {
        return promptRepository.findByDomain(domain);
    }

    public LlmResponse addResponse(LlmResponseRequest request) {
        Prompt prompt = promptRepository.findById(request.getPromptId())
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        LlmResponse response = new LlmResponse();
        response.setPrompt(prompt);
        response.setContent(request.getContent());
        response.setModelName(request.getModelName());
        response.setRewardScore(0.0);
        return responseRepository.save(response);
    }

    public List<LlmResponse> getResponsesByPrompt(Long promptId) {
        return responseRepository.findByPromptId(promptId);
    }
}
