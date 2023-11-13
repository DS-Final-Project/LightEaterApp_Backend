package com.example.LightEaterApp.Chat.controller;



import com.example.LightEaterApp.Chat.dto.chat.ChatResponseBodyDTO;
import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;


import com.example.LightEaterApp.Chat.dto.flask.FlaskResponseDTO;
import com.example.LightEaterApp.Chat.dto.response.ChatResponseDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.persistence.URIRepository;
import com.example.LightEaterApp.Chat.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Scanner;

import static com.example.LightEaterApp.Chat.service.FlaskService.ExtractData.extractConversations;

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
                log.info("relation:{}", relation);
                log.info("relationValue:{}", relationValue);
                //System.out.println(resultText);

                //flask에 보내기
                log.info("File uploaded successfully");

            } catch (IOException e) {
                e.printStackTrace();
                log.info("Failed to upload file");
            }
            chatEntity.setRelation(relationValue);
            chatEntity.setUserId(email);
            chatEntity.setChatDate(formattedDate);


            //!!모든값 임의설정 추후 ai파트와 연결시 가져올 값
            chatEntity.setResultNum(81);

            chatEntity.setDoubtText1("이게 노력하는사람 모습이가");
            chatEntity.setDoubtText2("본인이 존댓말한건 생각안하고 내기분이 나빠보인다니");
            //chatEntity.setResultNum((int) (Math.random() * 100));
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




            FlaskResponseDTO flaskResponseDTO = flaskService.sendChatWordsByImg(chatEntity.getChatData()).block();
            log.info("flaskService완료");


            chatEntity.setResultNum(flaskResponseDTO.getResultNum());
            chatEntity.setDoubtText1(flaskResponseDTO.getDoubtText1());
            chatEntity.setDoubtText2(flaskResponseDTO.getDoubtText2());
            chatEntity.setDoubtText3(flaskResponseDTO.getDoubtText3());
            chatEntity.setDoubtText4(flaskResponseDTO.getDoubtText4());
            chatEntity.setDoubtText5(flaskResponseDTO.getDoubtText5());

            //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴
            List<ChatEntity> chatEntities = chatService.createChatEntity(chatEntity);



            //수정
            ChatUploadRequestBodyDTO chatUploadRequestBodyDTO1 = new ChatUploadRequestBodyDTO(chatEntity);
            ChatResponseBodyDTO responsebodyDTO = new ChatResponseBodyDTO(chatUploadRequestBodyDTO1);

            responsebodyDTO.setResultNum(chatEntity.getResultNum());
            responsebodyDTO.setAnxietyScore(userEntity.getAnxietyScore());
            responsebodyDTO.setAvoidScore(userEntity.getAvoidScore());
            responsebodyDTO.setTestType(userEntity.getTestType());
            responsebodyDTO.setDoubtText1(flaskResponseDTO.getDoubtText1());
            responsebodyDTO.setDoubtText2(flaskResponseDTO.getDoubtText2());
            responsebodyDTO.setDoubtText3(flaskResponseDTO.getDoubtText3());
            responsebodyDTO.setDoubtText4(flaskResponseDTO.getDoubtText4());
            responsebodyDTO.setDoubtText5(flaskResponseDTO.getDoubtText5());



            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .data(responsebodyDTO)
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


    @PostMapping(value = "/file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadChatByFile(
            //@AuthenticationPrincipal String userId,
            @RequestHeader("email") String email,
            @RequestParam("file") MultipartFile file,
            @RequestParam("relation") String relation
            ///@RequestBody ChatUploadRequestBodyDTO chatUploadRequestBodyDTO
    ) {

        try {

            Date chatDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String formattedDate = dateFormat.format(chatDate);
            log.info("date:{}",formattedDate);

            log.info("chatEntity생성전");

            //!!이부분에서는 사실 ChatEntity만 생성  UserEntity는 생성되어있는 것을 가져와야함.-> 추후 수정
            ChatEntity chatEntity = new ChatEntity();
            log.info("chatEntity생성");
            //UserEntity userEntity = ChatUploadRequestBodyDTO.toUserEntity(chatUploadRequestBodyDTO);

            //!!userEntity의 userEmail, name없음 나중에 로그인 후 추가
            int relationValue = 0;
            //relation 변환해서 저장
            relationValue = Integer.parseInt(relation);
            FlaskResponseDTO flaskResponseDTO = new FlaskResponseDTO();



            try {
                //multipartfile text파일로 저장.
                InputStream initialStream = file.getInputStream();
                byte[] buffer = new byte[initialStream.available()];
                initialStream.read(buffer);
                log.info("targetfile생성전");

                File targetFile = new File("src/main/resources/targetFile.txt");
                //File targetFile = new File("/home/ec2-user/LightEaterApp_Backend/src/main/resources/targetFile.txt");
                log.info("targetfile생성후");


                try (OutputStream outStream = new FileOutputStream(targetFile)) {
                    outStream.write(buffer);
                }
                initialStream.close();

                String line="";

                //text파일 엔터 넣어주면서 읽기.
                Scanner scanner = new Scanner(new File("src/main/resources/targetFile.txt"));
               // Scanner scanner = new Scanner(new File("/home/ec2-user/LightEaterApp_Backend/src/main/resources/targetFile.txt"));
                log.info("2");


                while(scanner.hasNextLine()){
                    String str = scanner.nextLine();
                    line = line + str + "\n";
                    System.out.println(str);
                }
                log.info("3");

                //자바에서 텍스트 변환
                List<String> fileContent_list = extractConversations(line);
                //System.out.println(fileContent_list);
                //System.out.println(fileContent_list2);
                System.out.println(fileContent_list);

                //리스트를 인공지능에 보내줄 string변수로 바꾸기.
                String result = String.join("\n", fileContent_list);
                System.out.println("resultText:-------\n"+result);


                //flask에 보내기(result)
                flaskResponseDTO = flaskService.sendChatWordsByFile(result).block();
                log.info("flaskService완료");

                chatEntity.setChatData(result);
                log.info("resultText:{}", result);
                log.info("relation:{}", relation);
                log.info("relationValue:{}", relationValue);


            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Failed to upload file");
            }

            chatEntity.setRelation(relationValue);
            chatEntity.setUserId(email);
            chatEntity.setChatDate(formattedDate);





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


            chatEntity.setResultNum(flaskResponseDTO.getResultNum());
            chatEntity.setDoubtText1(flaskResponseDTO.getDoubtText1());
            chatEntity.setDoubtText2(flaskResponseDTO.getDoubtText2());
            chatEntity.setDoubtText3(flaskResponseDTO.getDoubtText3());
            chatEntity.setDoubtText4(flaskResponseDTO.getDoubtText4());
            chatEntity.setDoubtText5(flaskResponseDTO.getDoubtText5());


            //프론트에서 보내주면 전체 db말고 해당chatId entity만 리턴
            List<ChatEntity> chatEntities = chatService.createChatEntity(chatEntity);



            //수정
            ChatUploadRequestBodyDTO chatUploadRequestBodyDTO1 = new ChatUploadRequestBodyDTO(chatEntity);
            ChatResponseBodyDTO responsebodyDTO = new ChatResponseBodyDTO(chatUploadRequestBodyDTO1);

            responsebodyDTO.setResultNum(chatEntity.getResultNum());
            responsebodyDTO.setAnxietyScore(userEntity.getAnxietyScore());
            responsebodyDTO.setAvoidScore(userEntity.getAvoidScore());
            responsebodyDTO.setTestType(userEntity.getTestType());
            responsebodyDTO.setDoubtText1(flaskResponseDTO.getDoubtText1());
            responsebodyDTO.setDoubtText2(flaskResponseDTO.getDoubtText2());
            responsebodyDTO.setDoubtText3(flaskResponseDTO.getDoubtText3());
            responsebodyDTO.setDoubtText4(flaskResponseDTO.getDoubtText4());
            responsebodyDTO.setDoubtText5(flaskResponseDTO.getDoubtText5());





            ChatResponseDTO response = ChatResponseDTO.<ChatUploadRequestBodyDTO>builder()
                    .data(responsebodyDTO)
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