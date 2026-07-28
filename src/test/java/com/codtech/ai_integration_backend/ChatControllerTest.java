package com.codtech.ai_integration_backend;

import com.codtech.ai_integration_backend.controller.ChatController;
import com.codtech.ai_integration_backend.dto.ChatResponse;
import com.codtech.ai_integration_backend.service.AiProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatControllerTest {

    @Test
    public void testTalkToAi_Success() {
        // Arrange: Create a manual stub for our service to avoid needing complex testing frameworks
        AiProviderService mockService = new AiProviderService() {
            @Override
            public ChatResponse generateCompletion(String prompt) {
                return new ChatResponse("Hello Human!", 10, 20, 30);
            }
        };
        
        // Inject the manual mock into the controller
        ChatController controller = new ChatController(mockService);

        // Act: Call the endpoint directly
        ResponseEntity<ChatResponse> responseEntity = controller.talkToAi("Hello AI");

        // Assert: Verify the results match our expectations
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        ChatResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Hello Human!", body.content());
        assertEquals(10, body.promptTokens());
        assertEquals(20, body.completionTokens());
        assertEquals(30, body.totalTokens());
    }
}
