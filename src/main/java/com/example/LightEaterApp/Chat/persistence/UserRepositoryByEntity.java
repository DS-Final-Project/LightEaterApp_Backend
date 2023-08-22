package com.example.LightEaterApp.Chat.persistence;

import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryByEntity extends JpaRepository<UserEntity, String> {             //<테이블에 매핑될 엔티티 클래스, 이 엔티티의 기본키 타입>

    // List<ChatEntity> findByChatId(String chatId);
    // List<ChatEntity> findByUserId(String userId);
    UserEntity findByUserId(String userId);

}