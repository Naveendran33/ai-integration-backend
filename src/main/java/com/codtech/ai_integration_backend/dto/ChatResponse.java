package com.codtech.ai_integration_backend.dto;

public record ChatResponse(String content,int promptTokens,int completionTokens,int totalTokens) {
}
