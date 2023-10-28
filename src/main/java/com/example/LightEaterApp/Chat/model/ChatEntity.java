package com.example.LightEaterApp.Chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.net.URI;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="ChatTable")
public class ChatEntity {
    @Id
    //chatId는 자동 생성
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String chatId;

    @Column(length = 20000)
    private String chatData;


    private String userId;

    private int resultNum;


    //여기서 부터 엔티티 수정 2023-07-26
    private int relation;
    private String chatText;
    private String chatDate;
    private String doubtText1;
    private String doubtText2;
    //private String doubtSentence;


/*
    private String relation;
    private String chatFiles;
    private String[] chatImage;
    private String[] gSentence;
    private int resultNum;
    private Date chatDate;
*/
}
