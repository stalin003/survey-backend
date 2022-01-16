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
public class Users {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    private String email;

    @NotBlank
    @NotNull
    private String fullName;

    @NotBlank
    @NotNull
    private String DOB;

    @NotBlank
    @NotNull
    private String homeAddress;

    @NotBlank
    @NotNull
    private String SNI;

    @NotBlank
    @NotNull
    private String password;

    @NotNull
    @OneToMany(targetEntity = Roles.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users", orphanRemoval = true)
    private List<Roles> roles;

    @JsonManagedReference
    public List<Roles> getRoles() {
        return roles;
    }

    @NotNull
    private boolean active;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
