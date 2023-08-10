package com.example.LightEaterApp.Chat.dto.user;

import com.example.LightEaterApp.Chat.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelftestRequestBodyDTO {

    private float avoidScore;
    private float anxietyScore;
    private int testType;


    public static UserEntity toUserEntity(final SelftestRequestBodyDTO dto) {
        return UserEntity.builder()
                .avoidScore(dto.getAvoidScore())
                .anxietyScore(dto.getAnxietyScore())
                .testType(dto.getTestType())
                .build();
    }
}
