package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.dto.user.WithdrawResponseBodyDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.ChatService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @GetMapping("/withdraw")
    public ResponseEntity<?> selftestResult(@RequestHeader("email") String email){
        try {
            List<UserEntity> userEntities = userService.deletebyUserEmail(email);
            List<ChatEntity> chatEntities = chatService.deleteByEmail(email);
            log.info("삭제 완료");

            WithdrawResponseBodyDTO response = WithdrawResponseBodyDTO.builder()
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok().body(response);

        }


        catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();

            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(null);

        }
    }
}
