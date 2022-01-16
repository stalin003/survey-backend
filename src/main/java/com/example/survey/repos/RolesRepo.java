package com.example.survey.repos;

import com.example.survey.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepo extends JpaRepository<Roles, UUID> {
}
