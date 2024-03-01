package com.example.LightEaterApp.Chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="SolutionDetail")
public class SolutionDetailEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String detailId;

    private String solutionId;
    private String solutionTitle1;
    private String solutionContent1;
    private String solutionTitle2;
    private String solutionContent2;
    private String solutionTitle3;
    private String solutionContent3;
    private String solutionTitle4;
    private String solutionContent4;



    public String getSolutionTitle1() { return solutionTitle1; }
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
