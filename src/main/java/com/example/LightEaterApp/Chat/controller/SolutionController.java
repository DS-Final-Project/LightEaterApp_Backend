package com.example.LightEaterApp.Chat.controller;

import com.example.LightEaterApp.Chat.dto.response.ResponseListDTO;
import com.example.LightEaterApp.Chat.dto.solution.SolutionDetailDTO;
import com.example.LightEaterApp.Chat.dto.solution.SolutionResponseBodyDTO;
import com.example.LightEaterApp.Chat.model.SolutionDetailEntity;
import com.example.LightEaterApp.Chat.model.SolutionEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.service.SolutionService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
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


            //relation true 중복 데이터 쌓이는 거 수정
            // 사용자 관계를 확인하고 해당하는 솔루션 엔티티 추가
            if (userEntity.isRelation1()) {
                SolutionEntity solutionEntity1 = new SolutionEntity();
                solutionEntity1.setSolutionId("1");
                solutionEntity1.setKeyword("속박");
                solutionEntity1.setRelation(1);
                solutionEntity1.setSolutionTitle("갈등해결 방법이 \n궁금하세요?");
                solutionEntity1.setSolutionContent("content1");
                solutionService.createSolutionEntity(solutionEntity1);
                solutionEntities.add(solutionEntity1);

                SolutionEntity solutionEntity2 = new SolutionEntity();
                solutionEntity1.setSolutionId("2");
                solutionEntity2.setKeyword("집착");
                solutionEntity2.setRelation(1);
                solutionEntity2.setSolutionTitle("대처 방안을 \n찾고 계시나요?");
                solutionEntity2.setSolutionContent("content2");
                solutionService.createSolutionEntity(solutionEntity2);
                solutionEntities.add(solutionEntity2);
            }

            if (userEntity.isRelation2()) {
                SolutionEntity solutionEntity3 = new SolutionEntity();
                solutionEntity3.setSolutionId("3");
                solutionEntity3.setKeyword("왕따");
                solutionEntity3.setRelation(2);
                solutionEntity3.setSolutionTitle("가스라이터 친구 유형을 \n알려드려요!");
                solutionEntity3.setSolutionContent("content3");
                solutionService.createSolutionEntity(solutionEntity3);
                solutionEntities.add(solutionEntity3);

                SolutionEntity solutionEntity4 = new SolutionEntity();
                solutionEntity4.setSolutionId("4");
                solutionEntity4.setKeyword("험담");
                solutionEntity4.setRelation(2);
                solutionEntity4.setSolutionTitle("친구 간의 가스라이팅 \n대처 방안을 찾고 있나요?");
                solutionEntity4.setSolutionContent("content4");
                solutionService.createSolutionEntity(solutionEntity4);
                solutionEntities.add(solutionEntity4);

            }

            if (userEntity.isRelation3()) {
                SolutionEntity solutionEntity5 = new SolutionEntity();
                solutionEntity5.setSolutionId("5");
                solutionEntity5.setKeyword("무시");
                solutionEntity5.setRelation(3);
                solutionEntity5.setSolutionTitle("어떤 소통 방식을 \n취해야 할까요?");
                solutionEntity5.setSolutionContent("content5");
                solutionService.createSolutionEntity(solutionEntity5);
                solutionEntities.add(solutionEntity5);
                SolutionEntity solutionEntity6 = new SolutionEntity();
                solutionEntity6.setSolutionId("6");
                solutionEntity6.setKeyword("비난");
                solutionEntity6.setRelation(3);
                solutionEntity6.setSolutionTitle("상황에 따른 대처 방안이 \n궁금하신가요?");
                solutionEntity6.setSolutionContent("content6");
                solutionService.createSolutionEntity(solutionEntity6);
                solutionEntities.add(solutionEntity6);
            }

            if (userEntity.isRelation4()) {
                SolutionEntity solutionEntity7 = new SolutionEntity();
                solutionEntity7.setSolutionId("7");
                solutionEntity7.setKeyword("강요");
                solutionEntity7.setRelation(4);
                solutionEntity7.setSolutionTitle("나를 위해 취해야 하는 \n자세는 무엇일까요?");
                solutionEntity7.setSolutionContent("content7");
                solutionService.createSolutionEntity(solutionEntity7);
                solutionEntities.add(solutionEntity7);
                SolutionEntity solutionEntity8 = new SolutionEntity();
                solutionEntity7.setSolutionId("8");
                solutionEntity8.setKeyword("통제");
                solutionEntity8.setRelation(4);
                solutionEntity8.setSolutionTitle("나에 대한 확신이 없을 때 \n어떻게 해야 할까요?");
                solutionEntity8.setSolutionContent("content8");
                solutionService.createSolutionEntity(solutionEntity8);
                solutionEntities.add(solutionEntity8);
            }

            // 솔루션 엔티티를 DTO로 변환
            List<SolutionResponseBodyDTO> dtos = solutionEntities.stream()
                    .map(SolutionResponseBodyDTO::new)
                    .collect(Collectors.toList());
            log.info("solutionDTO:{} ",dtos.toString());

            ResponseListDTO response = ResponseListDTO.<SolutionResponseBodyDTO>builder()
                    .data(dtos)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseListDTO response = ResponseListDTO.<SolutionResponseBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/detail")
    public ResponseEntity<?> getSolutionDetail(@RequestParam("solutionId") String requestSolutionId) {
        try {

            SolutionDetailEntity solutionDetailEntity = solutionService.retrieveSolutionDetailByEntity(requestSolutionId);

            SolutionDetailDTO dto = new SolutionDetailDTO();
            dto.setSolutionTitle1(solutionDetailEntity.getSolutionTitle1());
            dto.setSolutionTitle2(solutionDetailEntity.getSolutionTitle2());
            dto.setSolutionTitle3(solutionDetailEntity.getSolutionTitle3());
            dto.setSolutionTitle4(solutionDetailEntity.getSolutionTitle4());
            dto.setSolutionContent1(solutionDetailEntity.getSolutionContent1());
            dto.setSolutionContent2(solutionDetailEntity.getSolutionContent2());
            dto.setSolutionContent3(solutionDetailEntity.getSolutionContent3());
            dto.setSolutionContent4(solutionDetailEntity.getSolutionContent4());


            log.info("solutionDetailDTO:{} ",dto.toString());



            ResponseListDTO<SolutionDetailDTO> response = ResponseListDTO.<SolutionDetailDTO>builder()
                    .data(Collections.singletonList(dto))
                    .build();

            return ResponseEntity.ok().body(response);
        }
        catch(Exception e) {
            String error = "Error while fetching solution detail: " + e.getMessage();
            //return ResponseEntity.badRequest().body(null);
            //return ResponseEntity.badRequest().body(null);
            ResponseListDTO response = ResponseListDTO.<SolutionDetailDTO>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    /*
    @GetMapping("/detail")
    public ResponseEntity<?> getSolutionDetail(@RequestParam("solutionId") String requestSolutionId) {
        try {
            List<SolutionDetailEntity> solutionDetailEntities = new ArrayList<>();
            log.info("{}" ,requestSolutionId);

            /*

            for (int i = 1; i <= 8; i++) {
                SolutionDetailEntity mockDetailEntity = new SolutionDetailEntity();
                mockDetailEntity.setSolutionId(Integer.toString(i));
                mockDetailEntity.setSolutionTitle1(i + "-mockTitle 1");
                mockDetailEntity.setSolutionContent1("mockContent 1");
                mockDetailEntity.setSolutionTitle2(i + "-mockTitle 2");
                mockDetailEntity.setSolutionContent2("mockContent 2");
                mockDetailEntity.setSolutionTitle3(i + "-mockTitle 3");
                mockDetailEntity.setSolutionContent3("mockContent 3");
                mockDetailEntity.setSolutionTitle4(i + "-mockTitle 4");
                mockDetailEntity.setSolutionContent4("mockContent 4");
                solutionDetailEntities.add(mockDetailEntity);
            }
*/
    /*

            // SolutionDetailEntity 객체를 SolutionDetailDTO 객체로 매핑
            SolutionDetailDTO solutionDetailDTO = new SolutionDetailDTO();
            // solutionDetailDTO.setSolutionId(requestSolutionId); // 요청한 solutionId 설정

            // requestSolutionId에 해당하는 SolutionDetailEntity를 찾아서 해당 내용을 설정
            for (SolutionDetailEntity entity : solutionDetailEntities) {
                log.info("{}",entity);
                if (entity.getSolutionId().equals(requestSolutionId)) {
                    solutionDetailDTO.setSolutionTitle1(entity.getSolutionTitle1());
                    solutionDetailDTO.setSolutionContent1(entity.getSolutionContent1());
                    solutionDetailDTO.setSolutionTitle2(entity.getSolutionTitle2());
                    solutionDetailDTO.setSolutionContent2(entity.getSolutionContent2());
                    solutionDetailDTO.setSolutionTitle3(entity.getSolutionTitle3());
                    solutionDetailDTO.setSolutionContent3(entity.getSolutionContent3());
                    solutionDetailDTO.setSolutionTitle4(entity.getSolutionTitle4());
                    solutionDetailDTO.setSolutionContent4(entity.getSolutionContent4());
                    // 필요한 title 및 content 속성 설정
                    break; // 원하는 데이터를 찾았으면 반복 종료
                }
            }

     */
            /*모든 속성을 매핑
            // SolutionDetailEntity 객체를 SolutionDetailDTO 객체로 매핑
            List<SolutionDetailDTO> dtos = solutionDetailEntities.stream()
                    .map(entity -> {
                        SolutionDetailDTO dto = new SolutionDetailDTO();
                        dto.setSolutionId(entity.getSolutionId());
                        dto.setSolutionTitle1(entity.getSolutionTitle1());
                        dto.setSolutionContent1(entity.getSolutionContent1());
                        dto.setSolutionTitle2(entity.getSolutionTitle2());
                        dto.setSolutionContent2(entity.getSolutionContent2());
                        dto.setSolutionTitle3(entity.getSolutionTitle3());
                        dto.setSolutionContent3(entity.getSolutionContent3());
                        dto.setSolutionTitle4(entity.getSolutionTitle4());
                        dto.setSolutionContent4(entity.getSolutionContent4());
                        return dto;
                    })
                    .collect(Collectors.toList());


            ResponseListDTO<SolutionDetailDTO> response = ResponseListDTO.<SolutionDetailDTO>builder()
                    .data(Collections.singletonList(solutionDetailDTO))
                    .build();

            return ResponseEntity.ok().body(response);
        }
        catch(Exception e) {
            String error = "Error while fetching solution detail: " + e.getMessage();
            //return ResponseEntity.badRequest().body(null);
            //return ResponseEntity.badRequest().body(null);
            ResponseListDTO response = ResponseListDTO.<SolutionDetailDTO>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }


             */
/*
    @PostMapping("/detail")
    public ResponseEntity<ResponseListDTO<SolutionDetailDTO>> createSolutionDetail(@RequestBody SolutionDetailDTO solutionDetailDTO) {
        try {
            // SolutionDetailDTO를 SolutionDetailEntity로 변환
            SolutionDetailEntity solutionDetailEntity = new SolutionDetailEntity();
            solutionDetailEntity.setSolutionId(solutionDetailDTO.getSolutionId());
            solutionDetailEntity.setSolutionTitle1(solutionDetailDTO.getSolutionTitle1());
            solutionDetailEntity.setSolutionContent1(solutionDetailDTO.getSolutionContent1());
            solutionDetailEntity.setSolutionTitle2(solutionDetailDTO.getSolutionTitle2());
            solutionDetailEntity.setSolutionContent2(solutionDetailDTO.getSolutionContent2());
            solutionDetailEntity.setSolutionTitle3(solutionDetailDTO.getSolutionTitle3());
            solutionDetailEntity.setSolutionContent3(solutionDetailDTO.getSolutionContent3());
            solutionDetailEntity.setSolutionTitle4(solutionDetailDTO.getSolutionTitle4());
            solutionDetailEntity.setSolutionContent4(solutionDetailDTO.getSolutionContent4());


            // SolutionDetailEntity를 저장
            SolutionDetailEntity savedSolutionDetail = solutionService.saveSolutionDetail(solutionDetailEntity);

            List<SolutionDetailEntity> solutionDetailEntities = new ArrayList<>();

            // SolutionDetailEntity 객체를 SolutionDetailDTO 객체로 매핑
            List<SolutionDetailDTO> dtos = solutionDetailEntities.stream()
                    .map(entity -> {
                        SolutionDetailDTO dto = new SolutionDetailDTO();
                        //dto.setSolutionId(entity.getSolutionId());
                        dto.setSolutionTitle1(entity.getSolutionTitle1());
                        dto.setSolutionContent1(entity.getSolutionContent1());
                        dto.setSolutionTitle2(entity.getSolutionTitle2());
                        dto.setSolutionContent2(entity.getSolutionContent2());
                        dto.setSolutionTitle3(entity.getSolutionTitle3());
                        dto.setSolutionContent3(entity.getSolutionContent3());
                        dto.setSolutionTitle4(entity.getSolutionTitle4());
                        dto.setSolutionContent4(entity.getSolutionContent4());
                        return dto;
                    })
                    .collect(Collectors.toList());

            ResponseListDTO<SolutionDetailDTO> response = ResponseListDTO.<SolutionDetailDTO>builder()
                    .data(dtos)
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = "Error while saving solution detail: " + e.getMessage();
            return ResponseEntity.badRequest().body(null);
        }
    }*/

}
