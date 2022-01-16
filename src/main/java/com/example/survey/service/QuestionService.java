package com.example.survey.service;

import com.example.survey.DTO.QuestionDTO;
import com.example.survey.model.Questions;
import com.example.survey.repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionsRepo questionsRepo;

    public Questions createQuestions(Questions questions) {
        return questionsRepo.save(questions);
    }

    public Questions getQuestionById(int id) {
        Optional<Questions> questionsOptional = questionsRepo.findById(id);
        return questionsOptional.orElse(null);
    }

    public boolean deleteQuestion(int id) {
        if (getQuestionById(id) != null) {
            questionsRepo.deleteById(id);

            return true;
        }
        return false;
    }

    public List<Questions> getAllQuestions(Integer pgNo, Integer pgSize) {
        Pageable pageable = PageRequest.of(pgNo, pgSize);

        Page<Questions> questions = questionsRepo.findAll(pageable);

        if (questions.hasContent()) {
            return questions.getContent();
        }

        return null;
    }

    public Questions updateQuestion(QuestionDTO q) {
        Questions questions = getQuestionById(q.getId());

        if (questions != null) {
            if (!questions.isAnswered()) {
                questions.setQuestionContent(q.getQuestionContent());
                questionsRepo.save(questions);
            }
        }

        return questions;
    }

}
