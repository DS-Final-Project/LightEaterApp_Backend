package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.mypage.MypageResponseBodyDTO;
import com.example.LightEaterApp.Chat.dto.mypage.PreviousMypageResponsebodyDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.dto.response.MypageResponseDTO;
import com.example.LightEaterApp.Chat.dto.response.ResponseMypageListDTO;
import com.example.LightEaterApp.Chat.dto.user.SelftestResponseBodyDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.ChatService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("mypage")
public class MypageController {
    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;

    @GetMapping("/chatResult/{chatId}")
    public ResponseEntity<?> getPreviousMypage(@RequestHeader("email") String email,
            @PathVariable("chatId") String requestChatId)
    {
        try {
            //String temporaryUserId = "userId";
            String chatId = requestChatId;

            ChatEntity chatEntity = chatService.retrieveByChatIDByEntity(chatId);
            UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);

            PreviousMypageResponsebodyDTO dto = new PreviousMypageResponsebodyDTO(chatEntity,userEntity);
            dto.setDoubtText1(chatEntity.getDoubtText1());
            dto.setDoubtText2(chatEntity.getDoubtText2());
            dto.setDoubtText3(chatEntity.getDoubtText3());
            dto.setDoubtText4(chatEntity.getDoubtText4());
            dto.setDoubtText5(chatEntity.getDoubtText5());



            MypageResponseDTO response = MypageResponseDTO.<MypageResponseBodyDTO>builder()
                    .data(dto)
                    .build();
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e) {
            String error = e.getMessage();
            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
    @DeleteMapping("chatResult/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable("chatId") String requestChatId){
        try {
            List<ChatEntity> entities = chatService.delete(chatService.retrieveByChatIDByEntity(requestChatId));
            log.info("지난분석 리스트 entities:{}",entities);

            SelftestResponseBodyDTO response =  SelftestResponseBodyDTO.<ChatUploadRequestBodyDTO>builder()
                    .build();
            log.info("지난 분석 리스트 response:{}", response);

            return ResponseEntity.ok().body(response);
        }


        catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();

            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(null);

        }

    }
    @GetMapping
    public ResponseEntity<?> getMypage(
            @RequestHeader("email") String email
            //@AuthenticationPrincipal String userId
            ) {
        try {


            //String temporaryUserId = "userId";
            List<ChatEntity> chatEntities = chatService.retrieveByUserID(email);
            log.info("chatEntities{}",chatEntities);
            List<UserEntity> userEntities = userService.retrieveByUserEmail(email);
            log.info("userEntities{}",userEntities);

            //chatentity가 없는 경우에 date는 null, name만 리턴
            if(chatEntities.size() == 0){
                //나중에 userEntity userId로 검색해서 이름 받아서 저장

                UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);
                String name = userEntity.getName();

                ResponseMypageListDTO<MypageResponseBodyDTO> response = ResponseMypageListDTO.<MypageResponseBodyDTO>builder()
                        .name(name)
                        .data(null)
                        .build();
                return ResponseEntity.ok().body(response);
            }
            else {
                UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);
                String name = userEntity.getName();
                /*
                //나중에 userEntity생성하면 리스트로 받을 필요없음 userId 가 Pk값일거기 때문에
                List<String> names = userEntities.stream()
                        .map(UserEntity::getName)
                        .collect(Collectors.toList());
                log.info("names{}", names);
                String name = names.get(0);

                 */


                List<MypageResponseBodyDTO> dtos = chatEntities.stream()
                        .map(chatEntity -> new MypageResponseBodyDTO(chatEntity))
                        .collect(Collectors.toList());

                ResponseMypageListDTO<MypageResponseBodyDTO> response = ResponseMypageListDTO.<MypageResponseBodyDTO>builder()
                        .name(name)
                        .data(dtos)
                        .build();
                return ResponseEntity.ok().body(response);
            }

        }
        catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
}
