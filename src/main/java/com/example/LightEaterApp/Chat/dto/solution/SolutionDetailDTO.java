package com.example.LightEaterApp.Chat.dto.solution;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class SolutionDetailDTO {
    private String solutionTitle1;
    private String solutionContent1;
    private String solutionTitle2;
    private String solutionContent2;
    private String solutionTitle3;
    private String solutionContent3;
    private String solutionTitle4;
    private String solutionContent4;

    public SolutionDetailDTO(String solutionTitle1, String solutionContent1,
                             String solutionTitle2, String solutionContent2,
                             String solutionTitle3, String solutionContent3,
                             String solutionTitle4, String solutionContent4) {
        this.solutionTitle1 = solutionTitle1;
        this.solutionContent1 = solutionContent1;
        this.solutionTitle2 = solutionTitle2;
        this.solutionContent2 = solutionContent2;
        this.solutionTitle3 = solutionTitle3;
        this.solutionContent3 = solutionContent3;
        this.solutionTitle4 = solutionTitle4;
        this.solutionContent4 = solutionContent4;
    }
}
