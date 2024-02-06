package com.example.LightEaterApp.Chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 500)
    private String userId;
    private String userEmail;
    private String name;

    private float avoidScore;
    private float anxietyScore;
    private int testType;

    private boolean relation1;
    private boolean relation2;
    private boolean relation3;
    private boolean relation4;
    //private enum relationList{};
}
