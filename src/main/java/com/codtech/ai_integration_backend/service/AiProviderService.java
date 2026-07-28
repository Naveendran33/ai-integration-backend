package com.codtech.ai_integration_backend.service;

import com.codtech.ai_integration_backend.dto.ChatResponse;


public interface AiProviderService {

    ChatResponse generateCompletion(String prompt);
}
