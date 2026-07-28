package com.codtech.ai_integration_backend.dto.openRouter;

import java.util.List;

public record OpenRouterRequest(String model, List<Message> messages) {
    public record Message(String role,String content){}
}
