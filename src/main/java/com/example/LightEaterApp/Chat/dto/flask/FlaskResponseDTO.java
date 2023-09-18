package com.example.LightEaterApp.Chat.dto.flask;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlaskResponseDTO {
    private int resultNum;
    private String doubtText1;
    private String doubtText2;

    // getters and setters
}