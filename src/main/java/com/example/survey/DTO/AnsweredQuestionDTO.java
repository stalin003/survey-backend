package com.example.survey.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnsweredQuestionDTO {
    private int questionId;
    private int optionId;
}
