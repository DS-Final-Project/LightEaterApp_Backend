package com.example.LightEaterApp.Chat.service;

import com.example.LightEaterApp.Chat.dto.solution.SolutionDetailDTO;
import com.example.LightEaterApp.Chat.model.SolutionDetailEntity;
import com.example.LightEaterApp.Chat.model.SolutionEntity;
import com.example.LightEaterApp.Chat.model.UserEntity;
import com.example.LightEaterApp.Chat.persistence.SolutionRepository;
import com.example.LightEaterApp.Chat.persistence.SolutionRepositoryByEntity;
import com.example.LightEaterApp.Chat.persistence.UserRepository;
import com.example.LightEaterApp.Chat.persistence.UserRepositoryByEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SolutionService {

        @Autowired
        private SolutionRepository repository;

        @Autowired
        private SolutionRepositoryByEntity solutionRepositoryByEntity;



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
    public SolutionDetailDTO getSolutionContentById(String solutionId) {
        SolutionDetailEntity solutionDetailEntity = solutionRepositoryByEntity.findBySolutionId(solutionId);
        return new SolutionDetailDTO(
                solutionDetailEntity.getSolutionTitle1(), solutionDetailEntity.getSolutionContent1(),
                solutionDetailEntity.getSolutionTitle2(), solutionDetailEntity.getSolutionContent2(),
                solutionDetailEntity.getSolutionTitle3(), solutionDetailEntity.getSolutionContent3(),
                solutionDetailEntity.getSolutionTitle4(), solutionDetailEntity.getSolutionContent4()
        );
    }

    public List<SolutionDetailDTO> getSolutionDetails(String solutionId) {
        List<SolutionDetailDTO> solutionDetails = new ArrayList<>();
/*
        for (int i = 1; i <= 4; i++) {
            // 여기에서 solutionId와 i를 조합하여 해당 솔루션의 상세 정보를 가져옵니다.
            String title = "Title " + i;
            String content = "Content " + i;
*/
        String title1 = "Title1";
        String content1 = "Content1";
        String title2 = "Title2";
        String content2 = "Content2";
        String title3 = "Title3";
        String content3 = "Content3";
        String title4 = "Title4";
        String content4 = "Content4";
        SolutionDetailDTO solutionDetail = new SolutionDetailDTO( title1, content1, title2, content2, title3, content3, title4, content4);
        solutionDetails.add(solutionDetail);


        return solutionDetails;
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


