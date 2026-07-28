package com.codtech.ai_integration_backend.service;

import com.codtech.ai_integration_backend.dto.ChatResponse;
import com.codtech.ai_integration_backend.dto.openRouter.OpenRouterRequest;
import com.codtech.ai_integration_backend.dto.openRouter.OpenRouterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Implementation of AiProviderService that communicates with the OpenRouter API.
 * This class builds the required request payload and parses the API response.
 */
@Service
@RequiredArgsConstructor
public class OpenRouterProviderService implements AiProviderService{

    // Injects the API key from application.properties or environment variables
    @Value("${api.key}")
    private String apiKey;

    // Injects the target AI model (e.g., google/gemma-4-26b-a4b-it:free)
    @Value("${model}")
    private String model;

    // Injects the base URL for OpenRouter (e.g., https://openrouter.ai/api/v1)
    @Value("${base-url}")
    private String url;

    // WebClient is used to make non-blocking, reactive HTTP requests
    private final WebClient webClient;

    /**
     * Sends a prompt to OpenRouter and returns the AI's response along with token usage.
     * 
     * @param prompt The user's input text
     * @return ChatResponse containing the generated text and token metrics
     */
    @Override
    public ChatResponse generateCompletion(String prompt) {

        // 1. Construct the payload in the format expected by OpenRouter (OpenAI format)
        OpenRouterRequest.Message userMessage = new OpenRouterRequest.Message("user", prompt);
        OpenRouterRequest request = new OpenRouterRequest(model, List.of(userMessage));

        // 2. Build the final endpoint URL
        String endpoint = url + "/chat/completions";

        try {
            // 3. Execute the POST request to the API
            OpenRouterResponse response = webClient.post()
                    .uri(endpoint)
                    .header("Content-Type", "application/json")
                    // Authenticate the request using the Bearer token
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OpenRouterResponse.class)
                    .block(); // .block() is used to wait for the response synchronously

            // 4. Extract the generated text from the response payload
            String responseText = response.choices().get(0).message().content();

            // 5. Extract token usage metadata for analytics or billing
            int promptTokens = response.usage() != null ? response.usage().prompt_tokens() : 0;
            int completionTokens = response.usage() != null ? response.usage().completion_tokens() : 0;
            int totalTokens = response.usage() != null ? response.usage().total_tokens() : 0;

            // 6. Return the standardized DTO back to the controller
            return new ChatResponse(responseText, promptTokens, completionTokens, totalTokens);
            
        } catch (Exception e) {
            // Log any errors that occur during the API call
            System.err.println("=== OPEN ROUTER SERVICE EXCEPTION ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
