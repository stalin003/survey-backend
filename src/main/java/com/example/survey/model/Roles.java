package com.example.survey.model;

import com.example.survey.util.UserRoles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Roles {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private UserRoles roles;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "fk_users")
    private Users users;

    @JsonBackReference
    public Users getUsers() {
        return users;
    }
}
