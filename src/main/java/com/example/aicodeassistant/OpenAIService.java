package com.example.aicodeassistant;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAIService {

    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public static String explain(String code) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            String prompt = """
            You are a senior software engineer.
            
            You are given code from a file with surrounding context.
            
            Your task:
            1. Explain what the selected code does
            2. Use file context if helpful
            3. Be concise
            4. If there are issues, mention them
            
            Context:
            %s
            """.formatted(code);

            String body = """
            {
              "model": "gpt-4o-mini",
              "messages": [
                {
                  "role": "user",
                  "content": "%s"
                }
              ]
              "max_tokens": 300
            }
            """.formatted(prompt.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return response.body();

        } catch (Exception e) {
            return "Error calling OpenAI API: " + e.getMessage();
        }
    }
}