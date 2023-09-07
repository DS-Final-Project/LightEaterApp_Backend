package com.example.LightEaterApp.Chat.persistence;
import com.example.LightEaterApp.Chat.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String>{
     List<UserEntity> findByUserId(String userId);
     List<UserEntity> findByUserEmail(String email);

     List<UserEntity> findByName(String name);
}
