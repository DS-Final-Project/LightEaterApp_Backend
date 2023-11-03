package com.example.LightEaterApp.Chat.service;

import com.example.LightEaterApp.Chat.dto.solution.SolutionDetailDTO;
import com.example.LightEaterApp.Chat.model.SolutionDetailEntity;
import com.example.LightEaterApp.Chat.model.SolutionEntity;
import com.example.LightEaterApp.Chat.persistence.SolutionDetailRepositoryByEntity;
import com.example.LightEaterApp.Chat.persistence.SolutionRepository;
import com.example.LightEaterApp.Chat.persistence.SolutionRepositoryByEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private SolutionRepositoryByEntity solutionRepositoryByEntity;
    private SolutionDetailRepositoryByEntity solutionDetailRepositoryByEntity;
    @Autowired
    public void setSolutionDetailRepositoryByEntity(SolutionDetailRepositoryByEntity solutionDetailRepositoryByEntity) {
        this.solutionDetailRepositoryByEntity = solutionDetailRepositoryByEntity;
    }


    // SolutionEntity 생성 및 저장
    public List<SolutionEntity> createSolutionEntity(final SolutionEntity entity) {

        // validate(entity);

        solutionRepository.save(entity);
/*
            log.info("Entity solutionID: {} is saved.", entity.getUserId());
            log.info("Entity userEmail: {} is saved.", entity.getUserEmail());
            log.info("Entity name: {} is saved.", entity.getName());
            log.info("Entity avoidscore: {} is saved.", entity.getAvoidScore());
            log.info("Entity anxietyscore: {} is saved.", entity.getAnxietyScore());
            log.info("Entity testType: {} is saved.", entity.getTestType());
*/
        log.info("solution is saved");
        //관계에 따른 SolutionEntity 목록 반환
        return solutionRepository.findByRelation(entity.getRelation());
    }

    // 관계에 따른 SolutionEntity 목록 검색
    public List<SolutionEntity> retrieveByRelation(final int relation) {
        return solutionRepository.findByRelation(relation);
    }
    public SolutionDetailEntity retrieveSolutionDetailByEntity(String solutionId) {

        return solutionDetailRepositoryByEntity.findBySolutionId(solutionId);
    }


    // SolutionDetailDTO를 solutionId로 검색
    //??
    public SolutionDetailDTO getSolutionDetailDTOById(String solutionId) {
        SolutionDetailEntity solutionDetailEntity = solutionDetailRepositoryByEntity.findBySolutionId(solutionId);

        if (solutionDetailEntity != null) {
            // SolutionDetailEntity에서 SolutionDetailDTO로 변환
            return new SolutionDetailDTO(
                    solutionDetailEntity.getSolutionTitle1(), solutionDetailEntity.getSolutionContent1(),
                    solutionDetailEntity.getSolutionTitle2(), solutionDetailEntity.getSolutionContent2(),
                    solutionDetailEntity.getSolutionTitle3(), solutionDetailEntity.getSolutionContent3(),
                    solutionDetailEntity.getSolutionTitle4(), solutionDetailEntity.getSolutionContent4()
            );

        }
        else {
            log.warn("Solution detail not found for solutionId: {}", solutionId);
            return null;
        }
    }

    // SolutionDetailEntity 저장
    public SolutionDetailEntity saveSolutionDetail(SolutionDetailEntity solutionDetailDTO) {
        // Save the SolutionDetailEntity to the database
        return solutionDetailRepositoryByEntity.save(solutionDetailDTO);
    }

    public List<SolutionDetailDTO> getSolutionDetails(String solutionId) {
        List<SolutionDetailDTO> solutionDetails = new ArrayList<>();

        // 해당 solutionId에 해당하는 SolutionDetailEntity를 가져옵니다.
        SolutionDetailEntity solutionDetailEntity = solutionDetailRepositoryByEntity.findBySolutionId(solutionId);

        if (solutionDetailEntity != null) {
            // SolutionDetailEntity에서 정보를 추출하여 DTO로 변환합니다.
            solutionDetails.add(new SolutionDetailDTO(
                    solutionDetailEntity.getSolutionTitle1(), solutionDetailEntity.getSolutionContent1(),
                    solutionDetailEntity.getSolutionTitle2(), solutionDetailEntity.getSolutionContent2(),
                    solutionDetailEntity.getSolutionTitle3(), solutionDetailEntity.getSolutionContent3(),
                    solutionDetailEntity.getSolutionTitle4(), solutionDetailEntity.getSolutionContent4()
            ));
        }
        return solutionDetails;
    }

    private void validate(final SolutionEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getSolutionId() == null) {
            log.warn("Unknown solution.");
            throw new RuntimeException("Unknown solution.");
        }
    }
}
