package com.example.LightEaterApp.Chat.dto.response;

import com.example.LightEaterApp.Chat.dto.chat.ChatResponseBodyDTO;
import com.example.LightEaterApp.Chat.dto.mypage.PreviousMypageResponsebodyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//http응답으로 사용
public class MypageResponseDTO {
    private String error;                   //에러메세지를 보내줌

    private PreviousMypageResponsebodyDTO data;
}