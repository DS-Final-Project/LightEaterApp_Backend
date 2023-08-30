package com.example.LightEaterApp.Chat.dto.mypage;

import com.example.LightEaterApp.Chat.dto.chat.ChatUploadRequestBodyDTO;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreviousMypageResponsebodyDTO {
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

    public PreviousMypageResponsebodyDTO (final ChatEntity chatEntity,UserEntity userEntity) {
        this.resultNum = chatEntity.getResultNum();
        this.avoidScore = userEntity.getAvoidScore();
        this.anxietyScore = userEntity.getAnxietyScore();
        this.testType = userEntity.getTestType();
        this.relation = chatEntity.getRelation();
        this.doubtText1 ="doubtText1";
        this.doubtText2 ="doubtText2";
        this.doubtText3 ="doubtText3";
        this.doubtText4 ="doubtText4";
        this.doubtText5 ="doubtText5";
    }
}

