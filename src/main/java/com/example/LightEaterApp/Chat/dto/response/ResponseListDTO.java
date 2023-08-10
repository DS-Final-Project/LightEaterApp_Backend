package com.example.LightEaterApp.Chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseListDTO<T> {
    private String error;                   //에러메세지를 보내줌

    private List<T> data;
}
