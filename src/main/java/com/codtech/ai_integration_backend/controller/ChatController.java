package com.codtech.ai_integration_backend.controller;

import com.codtech.ai_integration_backend.dto.ChatResponse;
import com.codtech.ai_integration_backend.service.AiProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller that handles incoming HTTP requests for the AI chat API.
 * It maps requests starting with "/api/chat" to the methods in this class.
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor // Lombok annotation to auto-generate a constructor for the final fields
public class ChatController {
    
    // Dependency Injection: The interface is used to decouple the controller from the specific AI implementation (OpenRouter).
    private final AiProviderService aiProviderService;

    /**
     * Endpoint to send a prompt to the AI and get a completion response.
     * Accessible via POST request at "/api/chat/completions".
     * 
     * @param prompt The user's input text (passed as a URL parameter).
     * @return ResponseEntity containing the ChatResponse object and an HTTP 200 OK status.
     */
    @PostMapping("/completions")
    public ResponseEntity<ChatResponse> talkToAi(@RequestParam String prompt){
        // Calls the service layer to process the prompt and communicate with the AI API
        ChatResponse chatResponse = aiProviderService.generateCompletion(prompt);

        // Returns the response object back to the client in JSON format
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }

}
