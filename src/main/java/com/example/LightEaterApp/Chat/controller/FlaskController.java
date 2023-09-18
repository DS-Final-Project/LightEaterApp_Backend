package com.example.LightEaterApp.Chat.controller;


import com.example.LightEaterApp.Chat.dto.flask.FlaskResponseDTO;
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
        this.webClient = webClientBuilder.baseUrl("http://172.20.27.44:5000").build();
    }

    @EventListener(ApplicationReadyEvent.class)

    public Mono<FlaskResponseDTO> sendChatWords() {
        String chatwords = "chatwords";
        Map<String, Object> chatWords = new HashMap<>();
        chatWords.put("chatWords", chatWords);

        return webClient.method(HttpMethod.POST)
                .uri("/percentage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatWords)
                .retrieve()
                .bodyToMono(FlaskResponseDTO.class)
                .doOnNext(response -> {
                    log.info("resultNum: {}", response.getResultNum());
                    log.info("doubtSentence1: {}", response.getDoubtSentence1());
                    log.info("doubtSentence2: {}", response.getDoubtSentence2());
                });
    }
}



