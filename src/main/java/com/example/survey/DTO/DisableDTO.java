package com.example.survey.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DisableDTO {
    private UUID id;
    private boolean disable;
}
