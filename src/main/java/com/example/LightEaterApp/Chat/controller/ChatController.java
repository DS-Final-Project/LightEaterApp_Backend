package com.example.LightEaterApp.Chat.controller;



import com.example.LightEaterApp.Chat.dto.chat.ChatResponseBodyDTO;
import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;


import com.example.LightEaterApp.Chat.dto.flask.FlaskResponseDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.URIEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.persistence.URIRepository;
import com.example.LightEaterApp.Chat.service.ChatService;
import com.example.LightEaterApp.Chat.service.FlaskService;
import com.example.LightEaterApp.Chat.service.OcrService;
import com.example.LightEaterApp.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("chatupload")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private FlaskService flaskService;
    @Autowired
    private URIRepository uriRepository;

    @Autowired
    private OcrService ocrService;

    //
    @PostMapping("/imgUpload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file,@RequestParam("relation") String relation) {
        // 파일 처리 로직
        try {
            //파일 로컬에 저장X
            // 이미지 파일 처리
            byte[] imageBytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();

            // relation 값 처리
            int relationValue = Integer.parseInt(relation);

            //ocr 처리
            String resultText = ocrService.detectText2(imageBytes);
            log.info("resultText:{}",resultText);
            System.out.println(resultText);

            //flask에 보내기
            return ResponseEntity.ok("File uploaded successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload file");
        }

    }
    @PostMapping(value = "/img",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadChatByImage(
            //@AuthenticationPrincipal String userId,
            @RequestHeader("email") String email,
            @RequestParam("image") MultipartFile file,
            @RequestParam("relation") String relation
            //@RequestBody ChatUploadRequestBodyDTO chatUploadRequestBodyDTO
    ) {
        try {

            Date chatDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String formattedDate = dateFormat.format(chatDate);
            log.info("date:{}", formattedDate);


            //!!이부분은 로그인 구현시 userId 로 대체되어 들어갈 부분 ->로그인 구현시 삭제
            //String temporaryUserId = "userId";

            //!!이부분에서는 사실 ChatEntity만 생성  UserEntity는 생성되어있는 것을 가져와야함.-> 추후 수정
            ChatEntity chatEntity = new ChatEntity();
            //UserEntity userEntity = ChatUploadRequestBodyDTO.toUserEntity(chatUploadRequestBodyDTO);

            //!!userEntity의 userEmail, name없음 나중에 로그인 후 추가

            int relationValue = 0;
            try {
                //파일 로컬에 저장X
                // 이미지 파일 처리
                byte[] imageBytes = file.getBytes();
                String originalFilename = file.getOriginalFilename();

                // relation 값 처리
                relationValue = Integer.parseInt(relation);

                //ocr 처리
                String resultText = ocrService.detectText2(imageBytes);
                chatEntity.setChatData(resultText);
                log.info("resultText:{}", resultText);
                //System.out.println(resultText);

                //flask에 보내기
                log.info("File uploaded successfully");

            } catch (IOException e) {
                e.printStackTrace();
                log.info("Failed to upload file");
            }
            chatEntity.setUserId(email);
            chatEntity.setChatDate(formattedDate);


            //!!모든값 임의설정 추후 ai파트와 연결시 가져올 값
            chatEntity.setResultNum((int) (Math.random() * 100));
            UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);
            if (relationValue == 1) {
                userEntity.setRelation1(true);

            } else if (relationValue == 2) {
                userEntity.setRelation2(true);
            } else if (relationValue == 3) {
                userEntity.setRelation3(true);
            } else if (relationValue == 4) {
                userEntity.setRelation4(true);
            }


            //!!여기는 userEntity에서 가져올 값
            /*
            userEntity.setUserId(jwtToken);
            userEntity.setUserEmail("4hyunhee@duksung.ac.kr");
            userEntity.setName("사현희");

             */
            //!!현재 임의생성 추후 삭제
            //float random1 = ((float) (Math.round(Math.random()*1000)/100.0));
            // float random2 = ((float) (Math.round(Math.random()*1000)/100.0));
/*
            userEntity.setAnxietyScore(random1);
            userEntity.setAvoidScore(random2);
            userEntity.setTestType(((int) (((Math.random()*10)%4)+1))); //1,2,3,4

 */


//flask 서버와 주고 받음

            FlaskResponseDTO flaskResponseDTO = flaskService.sendChatWords().block();


            chatEntity.setResultNum(flaskResponseDTO.getResultNum());


            //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴
            List<ChatEntity> chatEntities = chatService.createChatEntity(chatEntity);
            //!!로그인&자가진단 구현시 삭제될 부분
            //List<UserEntity> userEntities = userService.createUserEntity(userEntity);

/*
            URI imageUri = chatEntity.getChatData();

            try {
                URIEntity uriEntity = new URIEntity();
                uriEntity.setUri(imageUri);


                uriRepository.save(uriEntity);
                log.info("File URI saved successfully.");
                // return ResponseEntity.ok("File URI saved successfully.");
            } catch (Exception e) {
                log.info("Failed to save file URI: " + e.getMessage());
                //return ResponseEntity.badRequest().body("Failed to save file URI: " + e.getMessage());
            }

 */


            //수정
            ChatUploadRequestBodyDTO chatUploadRequestBodyDTO1 = new ChatUploadRequestBodyDTO(chatEntity);
            ChatResponseBodyDTO resoponsebodyDTO = new ChatResponseBodyDTO(chatUploadRequestBodyDTO1);

            resoponsebodyDTO.setResultNum(chatEntity.getResultNum());
            resoponsebodyDTO.setAnxietyScore(userEntity.getAnxietyScore());
            resoponsebodyDTO.setAvoidScore(userEntity.getAvoidScore());
            resoponsebodyDTO.setTestType(userEntity.getTestType());

/*
            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .flatMap(chatEntity1 -> userEntities.stream().map(userEntity1 -> new ChatUploadDTO(chatEntity, userEntity)))
                    .collect(Collectors.toList());
/*
            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .map(ChatUploadDTO::new)
                    .collect(Collectors.toList());

             dtos.addAll( userEntities.stream()
                    .map(ChatUploadDTO::new)
                    .collect(Collectors.toList())
             );


 /*
            //이부분은 리스트로 보내줄시 사용 -> 현재는 리스트가 아닌 한 채팅에 대해서만 보내주는 중
            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .map(entity -> new ChatUploadDTO(chatEntity, null))
                    .collect(Collectors.toList());
            dtos = userEntities.stream()
                    .map(entity -> new ChatUploadDTO(null, userEntity))
                    .collect(Collectors.toList());


/*
            List<ChatResponseBodyDTO> dtos = chatEntities.stream()
                    .map(ChatResponseBodyDTO::new)
                    .collect(Collectors.toList());\


            List<ChatResponseBodyDTO> dtos = chatEntities.stream()
                    .map(entity -> new ChatResponseBodyDTO(chatEntity, null))
                    .collect(Collectors.toList());

            dtos.addAll(userEntities.stream()
                    .map(entity -> new ChatResponseBodyDTO(null, userEntity))
                    .collect(Collectors.toList()));

*/

            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .data(resoponsebodyDTO)
                    .build();


            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
//재수정22


    @PostMapping(value = "/file ",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadChatByFile(
            //@AuthenticationPrincipal String userId,
            @RequestHeader("email") String email,
            @RequestBody ChatUploadRequestBodyDTO chatUploadRequestBodyDTO) {
        try {

            Date chatDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String formattedDate = dateFormat.format(chatDate);
            log.info("date:{}",formattedDate);




            ChatEntity chatEntity = ChatUploadRequestBodyDTO.toChatEntity(chatUploadRequestBodyDTO);
            //UserEntity userEntity = ChatUploadRequestBodyDTO.toUserEntity(chatUploadRequestBodyDTO);

            //userEntity의 userEmail, name없음
            //userEntity.setUserId(null);

            chatEntity.setUserId(email);
            chatEntity.setChatDate(formattedDate);

            //임의설정
            chatEntity.setResultNum((int) (Math.random()*100));

            UserEntity userEntity = userService.retrieveByUserEmailByEntity(email);

            if (chatUploadRequestBodyDTO.getRelation()==1){
                userEntity.setRelation1(true);

            } else if (chatUploadRequestBodyDTO.getRelation()==2) {
                userEntity.setRelation2(true);
            }
            else if (chatUploadRequestBodyDTO.getRelation()==3) {
                userEntity.setRelation3(true);
            }
            else if (chatUploadRequestBodyDTO.getRelation()==4) {
                userEntity.setRelation4(true);
            }


            //userEntity.setUserId(email);
            //userEntity.setUserEmail("4hyunhee@duksung.ac.kr");
            //userEntity.setName("사현희");
            //float random1 = ((float) (Math.round(Math.random()*1000)/100.0));
            //float random2 = ((float) (Math.round(Math.random()*1000)/100.0));

            //userEntity.setAnxietyScore(random1);
            //userEntity.setAvoidScore(random2);
            //userEntity.setTestType(((int) (((Math.random()*10)%4)+1))); //1,2,3,4





            FlaskResponseDTO flaskResponseDTO = flaskService.sendChatWords().block();


            chatEntity.setResultNum(flaskResponseDTO.getResultNum());


            //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴
            List<ChatEntity> chatEntities = chatService.createChatEntity(chatEntity);
            log.info("챗 컨트롤러 chatEntities:{}",chatEntities);
            //List<UserEntity> userEntities = userService.createUserEntity(userEntity);
            //log.info("챗 컨트롤러 userEntities:{}",userEntities);
/*
            URI fileUri = chatEntity.getChatData();

            try {
                URIEntity uriEntity = new URIEntity();
                uriEntity.setUri(fileUri);
                uriRepository.save(uriEntity);
                log.info("File URI saved successfully.");
                // return ResponseEntity.ok("File URI saved successfully.");
            } catch (Exception e) {
                log.info("Failed to save file URI: "+ e.getMessage());
                //return ResponseEntity.badRequest().body("Failed to save file URI: " + e.getMessage());
            }


*/


            ChatUploadRequestBodyDTO chatUploadRequestBodyDTO1 = new ChatUploadRequestBodyDTO(chatEntity);
            ChatResponseBodyDTO resoponsebodyDTO = new ChatResponseBodyDTO(chatUploadRequestBodyDTO1);

            resoponsebodyDTO.setResultNum(chatEntity.getResultNum());
            resoponsebodyDTO.setAnxietyScore(userEntity.getAnxietyScore());
            resoponsebodyDTO.setAvoidScore(userEntity.getAvoidScore());
            resoponsebodyDTO.setTestType(userEntity.getTestType());


/*
            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .flatMap(chatEntity1 -> userEntities.stream().map(userEntity1 -> new ChatUploadDTO(chatEntity, userEntity)))
                    .collect(Collectors.toList());
/*
            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .map(ChatUploadDTO::new)
                    .collect(Collectors.toList());

             dtos.addAll( userEntities.stream()
                    .map(ChatUploadDTO::new)
                    .collect(Collectors.toList())
             );


 /*

            List<ChatUploadDTO> dtos = chatEntities.stream()
                    .map(entity -> new ChatUploadDTO(chatEntity, null))
                    .collect(Collectors.toList());
            dtos = userEntities.stream()
                    .map(entity -> new ChatUploadDTO(null, userEntity))
                    .collect(Collectors.toList());


/*
            List<ChatResponseBodyDTO> dtos = chatEntities.stream()
                    .map(ChatResponseBodyDTO::new)
                    .collect(Collectors.toList());\


            List<ChatResponseBodyDTO> dtos = chatEntities.stream()
                    .map(entity -> new ChatResponseBodyDTO(chatEntity, null))
                    .collect(Collectors.toList());

            dtos.addAll(userEntities.stream()
                    .map(entity -> new ChatResponseBodyDTO(null, userEntity))
                    .collect(Collectors.toList()));

*/

            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .data(resoponsebodyDTO)
                    .build();


            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
/*
    @PostMapping("/file")
    public ResponseEntity<?> uploadChatByFile(
            //@AuthenticationPrincipal String userId,
            @RequestBody ChatDTO dto) {
        try {
            /*
            DTO 참고용
    private String chatId;
    private String userId;
    private int resultNum;
    private int relation;
    private String chatText;
    private Date chatDate;
    private String doubtSentence;

    private String userId;
    private float avoidScore;
    private float anxietyScore;
    private int testType;

            //responsebody 바꾸는중
            String temporaryUserId = "userId";             //이부분은 로그인 구현시 userId 로 대체되어 들어갈 부분 ->로그인 구현시 삭제


            ChatEntity entity = ChatDTO.toEntity(dto);

            entity.setChatId(null);

            entity.setUserId(temporaryUserId);

            int resultnum = flaskService.sendChatWords(entity.getChatWords()).block();

            entity.setResultNum(resultnum);

            List<ChatEntity> entities = service.createChatEntity(entity);         //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴

            List<ChatDTO> dtos = entities.stream()
                    .map(ChatDTO::new)
                    .collect(Collectors.toList());



            PostResponseTestDTO response = PostResponseTestDTO.<ChatDTO>builder()
                    .userId(entity.getUserId())
                    .chatId(entity.getChatId())
                    .resultNum(entity.getResultNum())
                    .chatWords(entity.getChatWords())
                    .build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }











*/

/*










    @PostMapping
    public ResponseEntity<?> createChat(
           //@AuthenticationPrincipal String userId,
            @RequestBody ChatDTO dto) {
        try {
            String temporaryUserId = "userId";             //이부분은 로그인 구현시 userId 로 대체되어 들어갈 부분 ->로그인 구현시 삭제


            ChatEntity entity = ChatDTO.toEntity(dto);

            entity.setChatId(null);

            entity.setUserId(temporaryUserId);

            entity.setResultNum(100);

            List<ChatEntity> entities = service.create(entity);
            int status = HttpStatus.OK.value();



            List<ChatDTO> dtos = entities.stream()
                    .map(ChatDTO::new)
                    .collect(Collectors.toList());

<<<<<<< HEAD

            String chatId = entity.getChatId();



            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .status(status)
                    .data(dtos)
                    .chatId(chatId)
                    .build();
=======
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }

    @PostMapping("/ByDataList")
    public ResponseEntity<?> createChatTest(
            //@AuthenticationPrincipal String userId,
            @RequestBody ChatDTO dto) {
        try {
            //responsebody 바꾸는중
            String temporaryUserId = "userId";             //이부분은 로그인 구현시 userId 로 대체되어 들어갈 부분 ->로그인 구현시 삭제


            ChatEntity entity = ChatDTO.toEntity(dto);

            entity.setChatId(null);

            entity.setUserId(temporaryUserId);

            entity.setResultNum(100);

            List<ChatEntity> entities = service.createTest(entity);

            List<ChatDTO> dtos = entities.stream()
                    .map(ChatDTO::new)
                    .collect(Collectors.toList());

            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .data(dtos).build();
>>>>>>> chatupload

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
<<<<<<< HEAD
            int status = HttpStatus.BAD_REQUEST.value();
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .status(status)
=======
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
>>>>>>> chatupload
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
<<<<<<< HEAD
=======
    //list빼고 변수만으로 테스트중
    @PostMapping("/justVariable")
    public ResponseEntity<?> createChatTestByVariable(
            //@AuthenticationPrincipal String userId,
            @RequestBody ChatDTO dto) {
        try {
            /*
    private String error;
    private String userId;
    private String chatId;
    private int resultNum;

            //responsebody 바꾸는중
            String temporaryUserId = "userId";             //이부분은 로그인 구현시 userId 로 대체되어 들어갈 부분 ->로그인 구현시 삭제


            ChatEntity entity = ChatDTO.toEntity(dto);

            entity.setChatId(null);

            entity.setUserId(temporaryUserId);

            int resultnum = flaskService.sendChatWords(entity.getChatWords()).block();

            entity.setResultNum(resultnum);

            List<ChatEntity> entities = service.createTest(entity);         //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴

            List<ChatDTO> dtos = entities.stream()
                    .map(ChatDTO::new)
                    .collect(Collectors.toList());



            PostResponseTestDTO response = PostResponseTestDTO.<ChatDTO>builder()
                    .userId(entity.getUserId())
                    .chatId(entity.getChatId())
                    .resultNum(entity.getResultNum())
                    .chatWords(entity.getChatWords())
                    .build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {                                      //예외 있는 경우 dto 대신 error 메세지 넣어 리턴
            String error = e.getMessage();
            PostResponseDTO<ChatDTO> response = PostResponseDTO.<ChatDTO>builder()
                    .error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
    //@PostMapping("AI")


>>>>>>> chatupload
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@RequestBody ChatDTO dto) {   //저장된 리스트 반환
        String temporaryUserId ="userId";

        ChatEntity entity = ChatDTO.toEntity(dto);

        entity.setUserId((temporaryUserId));
<<<<<<< HEAD


        List<ChatEntity> entities = service.retrieve(entity);
        int status = HttpStatus.OK.value();

        List<ChatDTO> dtos = entities.stream().map(ChatDTO::new).collect(Collectors.toList());

        GetResponseDTO<ChatDTO> response = GetResponseDTO.<ChatDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
=======
        entity.setResultNum(60);


        List<ChatEntity> entities = service.retrieve(entity);

        List<ChatDTO> dtos = entities.stream().map(ChatDTO::new).collect(Collectors.toList());
        //List<ChatDTO> responsedto = dtos.stream().map(ChatDTO::toArray).collect(toList());

        GetResponseDTO response = GetResponseDTO.<ChatDTO>builder()
                .userId(dtos.toString())
                .chatId(entity.getChatId())
                .chatWords(dtos.toString())
                .resultNum(100)
                .build();


        return ResponseEntity.ok().body(response);
    }*/

}