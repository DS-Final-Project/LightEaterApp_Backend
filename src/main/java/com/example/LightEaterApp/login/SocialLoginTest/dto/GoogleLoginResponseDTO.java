package com.example.LightEaterApp.login.SocialLoginTest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoogleLoginResponseDTO {
    private String error;
}
