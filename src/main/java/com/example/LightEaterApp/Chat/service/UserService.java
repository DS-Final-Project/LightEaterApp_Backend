package com.example.LightEaterApp.Chat.service;


import com.example.LightEaterApp.Chat.model.UserEntity;

import com.example.LightEaterApp.Chat.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserEntity> createUserEntity(final UserEntity entity) {

        validate(entity);
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

        repository.save(entity);

        log.info("Entity userId: {} is saved.", entity.getUserId());
        log.info("Entity userId: {} is saved.", entity.getUserEmail());
        log.info("Entity userId: {} is saved.", entity.getName());
        log.info("Entity userId: {} is saved.", entity.getAvoidScore());
        log.info("Entity userId: {} is saved.", entity.getAnxietyScore());
        log.info("Entity userId: {} is saved.", entity.getTestType());


        return repository.findByName(entity.getName());




    }
    public List<UserEntity> retrieveByUserId(final String userId) {


        return repository.findByUserId(userId);
    }

/*
    public List<UserEntity> retrieve(final UserEntity entity) {

        validate(entity);

        return repository.findByUserId(entity.getUserId());
    }
*/

    private void validate(final UserEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException(("Entity cannot be null."));
        } if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException(("Unknown user."));
        }
    }


}
