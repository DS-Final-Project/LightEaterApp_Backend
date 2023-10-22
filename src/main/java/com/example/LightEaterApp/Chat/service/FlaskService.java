package com.example.LightEaterApp.Chat.service;


import com.example.LightEaterApp.Chat.dto.flask.FlaskResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//깃 크라켄 수정중

@Slf4j
@Service
public class FlaskService {
    private final WebClient webClient;

    public FlaskService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://172.20.10.6:5000").build();
    }

    public static class ExtractData {
        public List<FlaskService.ExtractData.Pair<String, FlaskService.ExtractData.Point>> extractDataFromString(String content) {
            List<FlaskService.ExtractData.Pair<String, FlaskService.ExtractData.Point>> result = new ArrayList<>();

            String pattern = "Text :([가-힣a-zA-Z0-9!?@#$%^&*()]+)\\s+Position\\s+:+vertices\\s+\\{\\s+x:\\s+(\\d+)\\s+y:\\s+(\\d+)\\s+\\}";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(content);

            while (m.find()) {
                String text = m.group(1);
                int xValue = Integer.parseInt(m.group(2));
                int yValue = Integer.parseInt(m.group(3));
                result.add(new FlaskService.ExtractData.Pair<>(text, new FlaskService.ExtractData.Point(xValue, yValue)));
            }

            return result;
        }

        public  String formatTextByYValue(List<FlaskService.ExtractData.Pair<String, FlaskService.ExtractData.Point>> extractedData) {
            extractedData.sort(Comparator.comparingInt(o -> o.second.y));

            List<String> lines = new ArrayList<>();
            List<String> currentLine = new ArrayList<>();
            Integer previousY = null;

            for (FlaskService.ExtractData.Pair<String, FlaskService.ExtractData.Point> data : extractedData) {
                if (previousY != null && data.second.y - previousY >= 30) {
                    lines.add(String.join(" ", currentLine));
                    currentLine.clear();
                }

                currentLine.add(data.first);
                previousY = data.second.y;
            }

            if (!currentLine.isEmpty()) {
                lines.add(String.join(" ", currentLine));
            }

            List<String> cleanedLines = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("오전 ") || line.startsWith("오후 ")) {
                    line = line.substring(3);
                }
                if (line.endsWith(" 오전") || line.endsWith("오후")) {
                    line = line.substring(0, line.length() - 3);
                }
                cleanedLines.add(line.trim());
            }

            return String.join("\n", cleanedLines);
        }

        public static class Point {
            public int x, y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public static class Pair<T1, T2> {
            public T1 first;
            public T2 second;

            public Pair(T1 first, T2 second) {
                this.first = first;
                this.second = second;
            }
        }


    }
    //@EventListener(ApplicationReadyEvent.class)
    public Mono<FlaskResponseDTO> sendChatWords(String chatwords) {

        FlaskService.ExtractData extractor = new FlaskService.ExtractData();
        List<FlaskService.ExtractData.Pair<String, FlaskService.ExtractData.Point>> extractedData = extractor.extractDataFromString(chatwords);
        String formattedText = extractor.formatTextByYValue(extractedData);

        Map<String, Object> chatWordsMap = new HashMap<>();
        chatWordsMap.put("chatWords", formattedText);  //

        return webClient.method(HttpMethod.POST)
                .uri("/percentage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatWordsMap)
                .retrieve()
                .bodyToMono(FlaskResponseDTO.class)
                .doOnNext(response -> {
                    log.info("resultNum: {}", response.getResultNum());
                    log.info("doubtText1: {}", response.getDoubtText1());
                    log.info("doubtText2: {}", response.getDoubtText2());
                });
    }

}





