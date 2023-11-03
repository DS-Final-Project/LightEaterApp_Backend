package com.example.LightEaterApp.Chat.persistence;

import com.example.LightEaterApp.Chat.model.SolutionDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionDetailRepositoryByEntity extends JpaRepository<SolutionDetailEntity, String> {
    SolutionDetailEntity findBySolutionId(String solutionId);
}
