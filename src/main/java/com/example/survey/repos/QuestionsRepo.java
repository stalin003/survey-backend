package com.example.survey.repos;

import com.example.survey.model.Questions;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepo extends PagingAndSortingRepository<Questions, Integer> {
}
