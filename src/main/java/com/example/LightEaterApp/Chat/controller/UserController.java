package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.dto.user.SelftestRequestBodyDTO;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("selftest")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/post")
    public ResponseEntity<?> selftestResult(@RequestBody SelftestRequestBodyDTO selftestRequestBodyDTO){
        try {

            UserEntity userEntity = SelftestRequestBodyDTO.toUserEntity(selftestRequestBodyDTO);

            String temporaryUserId = "userId";
            userEntity.setUserId(temporaryUserId);
            userEntity.setUserEmail("4hyunhee@duksung.ac.kr");
            userEntity.setName("사현희");

            List<UserEntity> userEntities = userService.createUserEntity(userEntity);
            HttpStatus status = HttpStatus.OK;

            log.info("연동 성공");
            return ResponseEntity.ok().body(status);

        }

     catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
        String error = e.getMessage();
        ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                .error(error).build();

        return ResponseEntity.badRequest().body(null);

    }
    }
}

