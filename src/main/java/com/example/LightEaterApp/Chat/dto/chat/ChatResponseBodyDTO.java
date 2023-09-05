package com.example.LightEaterApp.Chat.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponseBodyDTO {
    private int resultNum;
    private float avoidScore;
    private float anxietyScore;
    private int testType;
    private int relation;
    private String doubtText1;
    private String doubtText2;
    private String doubtText3;
    private String doubtText4;
    private String doubtText5;

    public ChatResponseBodyDTO (final ChatUploadRequestBodyDTO chatUploadRequestBodyDTO) {
        //this.resultNum = chatUploadRequestBodyDTO.getResultNum();
        //this.avoidScore = chatUploadRequestBodyDTO.getAvoidScore();
        //this.anxietyScore = chatUploadRequestBodyDTO.getAnxietyScore();
        //this.testType = chatUploadRequestBodyDTO.getTestType();
        this.relation = chatUploadRequestBodyDTO.getRelation();
        this.doubtText1 ="doubtText1";
        this.doubtText2 ="doubtText2";
        this.doubtText3 ="doubtText3";
        this.doubtText4 ="doubtText4";
        this.doubtText5 ="doubtText5";
    }
/*

    public ChatResponseBodyDTO (final ChatEntity chatEntity) {
        this.resultNum = chatEntity.getResultNum();
        //this.avoidScore = userEntity.getAvoidScore();
        //this.anxietyScore = userEntity.getAnxietyScore();
        //this.testType = userEntity.getTestType();
        this.doubtText1 ="doubtText1";
        this.doubtText2 ="doubtText2";
        this.doubtText3 ="doubtText3";
        this.doubtText4 ="doubtText4";
        this.doubtText5 ="doubtText5";
    }
    public ChatResponseBodyDTO (final UserEntity userEntity) {
        //this.resultNum = chatEntity.getResultNum();
        this.avoidScore = userEntity.getAvoidScore();
        this.anxietyScore = userEntity.getAnxietyScore();
        this.testType = userEntity.getTestType();
        this.doubtText1 ="doubtText1";
        this.doubtText2 ="doubtText2";
        this.doubtText3 ="doubtText3";
        this.doubtText4 ="doubtText4";
        this.doubtText5 ="doubtText5";
    }


 */
}