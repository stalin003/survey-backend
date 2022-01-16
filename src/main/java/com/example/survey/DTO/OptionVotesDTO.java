package com.example.survey.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionVotesDTO {

    private int optionId;
    private int votes;

    public OptionVotesDTO(int optionId, int votes) {
        this.optionId = optionId;
        this.votes = votes;
    }
}
