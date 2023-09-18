package com.example.LightEaterApp.Chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FlaskService {
    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://172.20.10.6:5000").build();
    }


   //ChatWords 문자열을 flask서비스에 보내는 역할
    public Mono<Integer> sendChatWords(String ChatWords) {
        Map<String, Object> chatWords = new HashMap<>();
        chatWords.put("chatWords", "ChatWords");

        return webClient.method(HttpMethod.POST)
                .uri("/percentage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatWords)
                .retrieve()
                .bodyToMono(Map.class)
                .map(result -> (int) result.get("resultNum"));
    }
}

