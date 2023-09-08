package com.example.LightEaterApp.Chat.dto.solution;

import com.example.LightEaterApp.Chat.model.SolutionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SolutionResponseDTO {
    private String solutionId;
    private String keyword;
    private int relation;
    private String solutionTitle;

    private String solutionContent;

    public SolutionResponseDTO (SolutionEntity solutionEntity){
        this.solutionId = solutionEntity.getSolutionId();
        this.keyword = solutionEntity.getKeyword();
        this.relation = solutionEntity.getRelation();
        this.solutionTitle = solutionEntity.getSolutionTitle();
        this.solutionContent = solutionEntity.getSolutionContent();
    }

}
