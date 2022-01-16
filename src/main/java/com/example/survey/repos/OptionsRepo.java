package com.example.survey.repos;

import com.example.survey.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepo extends JpaRepository<Options, Integer> {
}
