package com.example.LightEaterApp.Chat.service;

import com.example.LightEaterApp.Chat.model.ChatEntity;
import com.example.LightEaterApp.Chat.persistence.ChatRepository;
import com.example.LightEaterApp.Chat.persistence.ChatRepositoryByEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {
    @Autowired
    private ChatRepository repository;
    @Autowired
    private ChatRepositoryByEntity chatRepositoryByEntity;

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
        //log.info("Entity chatWords: {} is saved.", entity.getChatData());
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
    public ChatEntity retrieveByUserIDByEntity(final String userId) {

        //validate(entity);

        return chatRepositoryByEntity.findByUserId(userId);
    }
    public List<ChatEntity> retrieveByChatID(final String chatId) {

        //validate(entity);

        return repository.findByChatId(chatId);
    }
    public ChatEntity retrieveByChatIDByEntity(final String chatId) {

        //validate(entity);

        return chatRepositoryByEntity.findByChatId(chatId);
    }

    public List<ChatEntity> delete(final ChatEntity entity) {
        //validate(entity);
        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity" , entity.getChatId(), e);
            throw new RuntimeException("error deleting entity " + entity.getChatId());
        }
        return retrieveByUserID(entity.getUserId());
    }
    public List<ChatEntity> deleteByEmail(final String email) {
        //validate(entity);
        try {
            log.info("chatService삭제");
            List<ChatEntity> entities = retrieveByUserID(email);
            log.info("entities{}",entities);
            repository.deleteAll(entities);
            log.info("삭제엔티티:{}",entities);
        } catch (Exception e) {
            log.error("error deleting entity" , retrieveByUserIDByEntity(email).getChatId(), e);
            throw new RuntimeException("error deleting entity " + retrieveByUserIDByEntity(email).getChatId());
        }
        log.info("deleteByEmail{} ",retrieveByUserIDByEntity(email).getUserId());
        return retrieveByUserID(retrieveByUserIDByEntity(email).getUserId());
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