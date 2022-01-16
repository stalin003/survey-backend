package com.example.survey.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @NotBlank
    @NotNull
    private String questionContent;

    private boolean isAnswered;

    @NotNull
    @OneToMany(targetEntity = Options.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "questions", orphanRemoval = true)
    private List<Options> options;

    @JsonManagedReference
    public List<Options> getOptions() {
        return options;
    }

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
