package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.chat.ChatResponseBodyDTO;
import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.dto.response.ResponseListDTO;
import com.example.LightEaterApp.Chat.dto.solution.SolutionResponseDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.SolutionEntity;
import com.example.LightEaterApp.Chat.model.URIEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.SolutionService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("solution")
public class SolutionController {
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getSolution(
            @RequestHeader("email") String email) {
        try {
            // 솔루션 엔티티를 저장할 빈 리스트 생성
            List<SolutionEntity> solutionEntities = new ArrayList<>();

            // 이메일로 사용자 엔티티 가져오기
            UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);

            // 사용자 관계를 확인하고 해당하는 솔루션 엔티티 추가
            if (userEntity.isRelation1()) {
                SolutionEntity solutionEntity1 = new SolutionEntity();
                solutionEntity1.setSolutionId("1");
                solutionEntity1.setKeyword("keyword1");
                solutionEntity1.setRelation(1);
                solutionEntity1.setSolutionTitle("title1");
                solutionEntity1.setSolutionContent("content1");
                solutionService.createSolutionEntity(solutionEntity1);
                solutionEntities.add(solutionEntity1);

                SolutionEntity solutionEntity2 = new SolutionEntity();
                solutionEntity1.setSolutionId("2");
                solutionEntity2.setKeyword("keyword2");
                solutionEntity2.setRelation(1);
                solutionEntity2.setSolutionTitle("title2");
                solutionEntity2.setSolutionContent("content2");
                solutionService.createSolutionEntity(solutionEntity2);
                solutionEntities.add(solutionEntity2);
            }

            if (userEntity.isRelation2()) {
                SolutionEntity solutionEntity3 = new SolutionEntity();
                solutionEntity3.setSolutionId("3");
                solutionEntity3.setKeyword("keyword3");
                solutionEntity3.setRelation(2);
                solutionEntity3.setSolutionTitle("title3");
                solutionEntity3.setSolutionContent("content3");
                solutionService.createSolutionEntity(solutionEntity3);
                solutionEntities.add(solutionEntity3);

                SolutionEntity solutionEntity4 = new SolutionEntity();
                solutionEntity4.setSolutionId("4");
                solutionEntity4.setKeyword("keyword4");
                solutionEntity4.setRelation(2);
                solutionEntity4.setSolutionTitle("title4");
                solutionEntity4.setSolutionContent("content4");
                solutionService.createSolutionEntity(solutionEntity4);
                solutionEntities.add(solutionEntity4);

            }

            if (userEntity.isRelation3()) {
                SolutionEntity solutionEntity5 = new SolutionEntity();
                solutionEntity5.setSolutionId("5");
                solutionEntity5.setKeyword("keyword5");
                solutionEntity5.setRelation(3);
                solutionEntity5.setSolutionTitle("title5");
                solutionEntity5.setSolutionContent("content5");
                solutionService.createSolutionEntity(solutionEntity5);
                solutionEntities.add(solutionEntity5);
                SolutionEntity solutionEntity6 = new SolutionEntity();
                solutionEntity6.setSolutionId("6");
                solutionEntity6.setKeyword("keyword6");
                solutionEntity6.setRelation(3);
                solutionEntity6.setSolutionTitle("title6");
                solutionEntity6.setSolutionContent("content6");
                solutionService.createSolutionEntity(solutionEntity6);
                solutionEntities.add(solutionEntity6);
            }

            if (userEntity.isRelation4()) {
                SolutionEntity solutionEntity7 = new SolutionEntity();
                solutionEntity7.setSolutionId("7");
                solutionEntity7.setKeyword("keyword7");
                solutionEntity7.setRelation(4);
                solutionEntity7.setSolutionTitle("title7");
                solutionEntity7.setSolutionContent("content7");
                solutionService.createSolutionEntity(solutionEntity7);
                solutionEntities.add(solutionEntity7);
                SolutionEntity solutionEntity8 = new SolutionEntity();
                solutionEntity7.setSolutionId("8");
                solutionEntity8.setKeyword("keyword8");
                solutionEntity8.setRelation(4);
                solutionEntity8.setSolutionTitle("title8");
                solutionEntity8.setSolutionContent("content8");
                solutionService.createSolutionEntity(solutionEntity8);
                solutionEntities.add(solutionEntity8);
            }

            // 솔루션 엔티티를 DTO로 변환
            List<SolutionResponseDTO> dtos = solutionEntities.stream()
                    .map(SolutionResponseDTO::new)
                    .collect(Collectors.toList());

            ResponseListDTO response = ResponseListDTO.<SolutionResponseDTO>builder()
                    .data(dtos)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseListDTO response = ResponseListDTO.<SolutionResponseDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
    }
/*
        try {
            //db연결시 삭제할 부분---------------------------------------------
            List<SolutionEntity> entities = new ArrayList<>();
            SolutionEntity solutionEntity1 = new SolutionEntity();
            solutionEntity1.setSolutionId("1");
            solutionEntity1.setKeyword("keyword1");
            solutionEntity1.setRelation(1);
            solutionEntity1.setSolutionTitle("title1");
            solutionEntity1.setSolutionContent("content1");
            log.info("solutionEntity1:{}",solutionEntity1);
            solutionService.createSolutionEntity(solutionEntity1);
            entities.add(solutionEntity1);

            SolutionEntity solutionEntity2 = new SolutionEntity();
            solutionEntity1.setSolutionId("2");
            solutionEntity2.setKeyword("keyword2");
            solutionEntity2.setRelation(1);
            solutionEntity2.setSolutionTitle("title2");
            solutionEntity2.setSolutionContent("content2");
            solutionService.createSolutionEntity(solutionEntity2);
            entities.add(solutionEntity2);

            SolutionEntity solutionEntity3 = new SolutionEntity();
            solutionEntity1.setSolutionId("3");
            solutionEntity3.setKeyword("keyword3");
            solutionEntity3.setRelation(2);
            solutionEntity3.setSolutionTitle("title3");
            solutionEntity3.setSolutionContent("content3");
            solutionService.createSolutionEntity(solutionEntity3);
            entities.add(solutionEntity3);

            SolutionEntity solutionEntity4 = new SolutionEntity();
            solutionEntity1.setSolutionId("4");
            solutionEntity4.setKeyword("keyword4");
            solutionEntity4.setRelation(2);
            solutionEntity4.setSolutionTitle("title4");
            solutionEntity4.setSolutionContent("content4");
            solutionService.createSolutionEntity(solutionEntity4);
            entities.add(solutionEntity4);

            SolutionEntity solutionEntity5 = new SolutionEntity();
            solutionEntity1.setSolutionId("5");
            solutionEntity5.setKeyword("keyword5");
            solutionEntity5.setRelation(3);
            solutionEntity5.setSolutionTitle("title5");
            solutionEntity5.setSolutionContent("content5");
            solutionService.createSolutionEntity(solutionEntity5);
            entities.add(solutionEntity5);
            SolutionEntity solutionEntity6 = new SolutionEntity();
            solutionEntity1.setSolutionId("6");
            solutionEntity6.setKeyword("keyword6");
            solutionEntity6.setRelation(3);
            solutionEntity6.setSolutionTitle("title6");
            solutionEntity6.setSolutionContent("content6");
            solutionService.createSolutionEntity(solutionEntity6);
            entities.add(solutionEntity6);

            SolutionEntity solutionEntity7 = new SolutionEntity();
            solutionEntity1.setSolutionId("7");
            solutionEntity7.setKeyword("keyword7");
            solutionEntity7.setRelation(4);
            solutionEntity7.setSolutionTitle("title7");
            solutionEntity7.setSolutionContent("content7");
            solutionService.createSolutionEntity(solutionEntity7);
            entities.add(solutionEntity7);
            SolutionEntity solutionEntity8 = new SolutionEntity();
            solutionEntity1.setSolutionId("8");
            solutionEntity8.setKeyword("keyword8");
            solutionEntity8.setRelation(4);
            solutionEntity8.setSolutionTitle("title8");
            solutionEntity8.setSolutionContent("content8");
            solutionService.createSolutionEntity(solutionEntity8);
            entities.add(solutionEntity8);

            //db연결시 삭제할 부분---------------------------------------------




        UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);
        log.info("relation1:{}",userEntity.isRelation1());
        log.info("relation2:{}",userEntity.isRelation2());
        log.info("relation3:{}",userEntity.isRelation3());
        log.info("relation4:{}",userEntity.isRelation4());

            //리스트가 중복적으로 쌓여서 이 부분 수정
            List<SolutionEntity> solutionEntities =null;
            solutionEntities  = new ArrayList<>();
        if (userEntity.isRelation1()) {

            solutionEntities.addAll(solutionService.retrieveByRelation(1));
        }
         if (userEntity.isRelation2()) {
             solutionEntities.addAll(solutionService.retrieveByRelation(2));
        }
         if (userEntity.isRelation3()) {
             solutionEntities.addAll(solutionService.retrieveByRelation(3));
        }
         if (userEntity.isRelation4()) {
             solutionEntities.addAll(solutionService.retrieveByRelation(4));
        }

         log.info("entities:{}",solutionEntities);
        //솔루션 엔티티 저장 -> responsebody로 변경 후 list로 return하도록 수정

        List<SolutionResponseDTO> dtos = solutionEntities.stream()
                .map(SolutionResponseDTO::new)
                .collect(Collectors.toList());



            ResponseListDTO response = ResponseListDTO.<SolutionResponseDTO>builder()
                    .data(dtos)
                    .build();


            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
        ResponseListDTO response = ResponseListDTO.<SolutionResponseDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
}


 */