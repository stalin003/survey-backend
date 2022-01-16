package com.example.survey.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidentRespondDTO {
    public ResidentRespondDTO(int questionId, int peopleAttempted) {
        this.questionId = questionId;
        this.peopleAttempted = peopleAttempted;
    }

    private int questionId;
    private int peopleAttempted;
}
