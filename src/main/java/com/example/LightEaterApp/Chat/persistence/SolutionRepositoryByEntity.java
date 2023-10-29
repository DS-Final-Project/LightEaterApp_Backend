package com.example.LightEaterApp.Chat.persistence;

import com.example.LightEaterApp.Chat.model.SolutionDetailEntity;
import com.example.LightEaterApp.Chat.model.SolutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepositoryByEntity extends JpaRepository<SolutionEntity, SolutionDetailEntity> {

    //public SolutionEntity findBySolutionId(final String solutionId);
    public SolutionDetailEntity findBySolutionId(final String solutionId);
}
