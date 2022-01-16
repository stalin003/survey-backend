package com.example.survey.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @NotBlank
    @NotNull
    private String optionContent;

    @ManyToOne(targetEntity = Questions.class)
    @JoinColumn(name = "fk_questions")
    private Questions questions;

    @JsonBackReference
    public Questions getQuestions() {
        return questions;
    }
}
