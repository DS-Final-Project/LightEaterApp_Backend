package com.example.LightEaterApp.Chat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SolutionDetailEntity {
    @Id
    // private String solutionDetailId;
    private String solutionId;
    private String solutionTitle1;
    private String solutionContent1;
    private String solutionTitle2;
    private String solutionContent2;
    private String solutionTitle3;
    private String solutionContent3;
    private String solutionTitle4;
    private String solutionContent4;

    public String getSolutionTitle1() {
        return solutionTitle1;
    }
    public String getSolutionTitle2() {
        return solutionTitle2;
    }
    public String getSolutionTitle3() {
        return solutionTitle3;
    }
    public String getSolutionTitle4() {
        return solutionTitle4;
    }
    public String getSolutionContent1() {
        return solutionContent1;
    }
    public String getSolutionContent2() {
        return solutionContent2;
    }
    public String getSolutionContent3() {
        return solutionContent3;
    }
    public String getSolutionContent4() {
        return solutionContent4;
    }
}
