package com.example.LightEaterApp.Chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.*;


@Slf4j
@Service
public class ExtractDataService {
    public List<Pair<String, Point>> extractDataFromString(String content) {
        List<Pair<String, Point>> result = new ArrayList<>();

        String pattern = "Text :([가-힣a-zA-Z0-9!?@#$%^&*()]+)\\s+Position\\s+:+vertices\\s+\\{\\s+x:\\s+(\\d+)\\s+y:\\s+(\\d+)\\s+\\}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);

        while (m.find()) {
            String text = m.group(1);
            int xValue = Integer.parseInt(m.group(2));
            int yValue = Integer.parseInt(m.group(3));
            result.add(new Pair<>(text, new Point(xValue, yValue)));
        }

        return result;
    }
    public String formatTextByYValue(List<Pair<String, Point>> extractedData) {
        extractedData.sort(Comparator.comparingInt(o -> o.second.y));

        List<String> lines = new ArrayList<>();
        List<String> currentLine = new ArrayList<>();
        Integer previousY = null;

        for (Pair<String, Point> data : extractedData) {
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

    public class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }


}