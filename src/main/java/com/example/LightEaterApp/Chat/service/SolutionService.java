package com.example.LightEaterApp.Chat.service;

import com.example.LightEaterApp.Chat.model.SolutionEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.persistence.SolutionRepository;
import com.example.LightEaterApp.Chat.persistence.UserRepository;
import com.example.LightEaterApp.Chat.persistence.UserRepositoryByEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SolutionService {

        @Autowired
        private SolutionRepository repository;


        public List<SolutionEntity> createSolutionEntity(final SolutionEntity entity) {

           // validate(entity);

            repository.save(entity);
/*
            log.info("Entity solutionID: {} is saved.", entity.getUserId());
            log.info("Entity userEmail: {} is saved.", entity.getUserEmail());
            log.info("Entity name: {} is saved.", entity.getName());
            log.info("Entity avoidscore: {} is saved.", entity.getAvoidScore());
            log.info("Entity anxietyscore: {} is saved.", entity.getAnxietyScore());
            log.info("Entity testType: {} is saved.", entity.getTestType());
*/

            log.info("solution is saved");
            return repository.findByRelation(entity.getRelation());




        }
        public List<SolutionEntity> retrieveByRelation(final int relation) {


            return repository.findByRelation(relation);
        }

/*
    public List<UserEntity> retrieve(final UserEntity entity) {

        validate(entity);

        return repository.findByUserId(entity.getUserId());
    }
*/

        private void validate(final SolutionEntity entity) {
            if(entity == null) {
                log.warn("Entity cannot be null.");
                throw new RuntimeException(("Entity cannot be null."));
            } if(entity.getSolutionId() == null) {
                log.warn("Unknown solution.");
                throw new RuntimeException(("Unknown solution."));
            }
        }


    }


