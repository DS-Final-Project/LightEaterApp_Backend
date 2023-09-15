package com.example.LightEaterApp.Chat.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FlaskController {
    private final WebClient webClient;

    public FlaskController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://172.20.11.56:5000").build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public Mono<String> sendChatWords() {
        String chatwords = "chatwords";
        Map<String, Object> chatWords = new HashMap<>();
        chatWords.put("chatWords", chatWords);


        return webClient.method(HttpMethod.POST)
                .uri("/percentage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatWords)
                .retrieve()
                .bodyToMono(Map.class)
                .map(result -> {
                    Map<String, Object> resultMap = (Map<String, Object>) result;
                    String resultNum = "resultNum: " + resultMap.get("resultNum");
                    String doubtSentence1 = "doubtSentence1: " + resultMap.get("doubtsentence1");
                    String doubtSentence2 = "doubtSentence2: " + resultMap.get("doubtsentence2");
                    log.info("resultnum: {}",resultNum);
                    log.info("resultnum: {}",doubtSentence1);
                    log.info("resultnum: {}",doubtSentence2);
                    return resultNum + ", " + doubtSentence1 + ", " + doubtSentence2;

                });

    }
}



