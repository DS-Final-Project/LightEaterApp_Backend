package com.example.LightEaterApp.Chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="UserTable")
public class UserEntity {
    @Id
    //@GeneratedValue(generator = "system-uuid")
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    //로그인이후에 userEntity생성 만들면 temporaryId삭제
    private String temporaryId;
    private String userId;
    private String userEmail;
    private String name;

    private float avoidScore;
    private float anxietyScore;
    private int testType;
    //private enum relationList{};
}
