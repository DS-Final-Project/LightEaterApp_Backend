package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.dto.user.SelftestRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.user.SelftestResponseBodyDTO;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("selftest")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/post")
    public ResponseEntity<?> selftestResult(@RequestHeader("email") String email, @RequestBody SelftestRequestBodyDTO selftestRequestBodyDTO){
        try {
            UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);
            userEntity.setAvoidScore((float)(Math.floor(selftestRequestBodyDTO.getAvoidScore() * 100) / 100.0));
            userEntity.setAnxietyScore((float)(Math.floor(selftestRequestBodyDTO.getAnxietyScore()*100)/100.0));
            userEntity.setTestType(selftestRequestBodyDTO.getTestType());


            List<UserEntity> userEntities = userService.createUserEntity(userEntity);

            SelftestResponseBodyDTO response =  SelftestResponseBodyDTO.<ChatUploadRequestBodyDTO>builder()
                    .build();


            log.info("연동 성공");
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

