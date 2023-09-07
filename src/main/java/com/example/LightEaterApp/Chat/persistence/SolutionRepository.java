package com.example.LightEaterApp.Chat.persistence;

import com.example.LightEaterApp.Chat.model.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, String> {

    List<SolutionEntity> findByRelation(int relation);
}
