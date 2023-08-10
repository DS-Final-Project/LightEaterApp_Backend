package com.example.LightEaterApp.Chat.dto.mypage;

import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.ChatService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MypageResponseBodyDTO {
    private String chatId;
    private String chatDate;
    private int resultNum;


public MypageResponseBodyDTO(final ChatEntity chatEntity){

    this.chatId = chatEntity.getChatId();
    this.chatDate = chatEntity.getChatDate();
    this.resultNum = chatEntity.getResultNum();

}
}
