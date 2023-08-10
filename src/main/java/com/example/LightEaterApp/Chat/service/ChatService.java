package com.example.LightEaterApp.Chat.service;

import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.persistence.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {
    @Autowired
    private ChatRepository repository;

    public List<ChatEntity> createChatEntity(final ChatEntity entity) {

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

        log.info("Entity chatId: {} is saved.", entity.getChatId());
        log.info("Entity chatWords: {} is saved.", entity.getChatData());
        log.info("Entity resultnum: {} is saved.", entity.getResultNum());
        log.info("Entity date: {} is saved.", entity.getChatDate());
        log.info("relation:{}", entity.getRelation());


        return repository.findByUserId(entity.getUserId());




    }

    public List<ChatEntity> create(final ChatEntity entity) {

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

        log.info("Entity chatId: {} is saved.", entity.getChatId());
        log.info("Entity chatWords: {} is saved.", entity.getChatData());
        log.info("Entity resultnum: {} is saved.", entity.getResultNum());
        log.info("Entity chatdate: {} is saved.", entity.getChatDate());


        return repository.findByChatId(entity.getChatId());




    }
    public List<ChatEntity> retrieveByUserID(final String userId) {

        //validate(entity);

        return repository.findByUserId(userId);
    }
    /*
    public List<ChatEntity> retrieve(final ChatEntity entity) {

        validate(entity);

        return repository.findByChatId(entity.getChatId());
    }



     */
    private void validate(final ChatEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException(("Entity cannot be null."));
        } if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException(("Unknown user."));
        }
    }


}