package com.example.survey.controller;

import com.example.survey.DTO.QuestionDTO;
import com.example.survey.model.Questions;
import com.example.survey.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@Validated
@RequestMapping(path = "api/question")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping(path = "")
    public ResponseEntity<List<Questions>> getAllQuestion() {
        log.info("getting all questions");

        return new ResponseEntity<>(service.getAllQuestions(0, Integer.MAX_VALUE), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Questions> getQuestionById(@PathVariable int id) {
        log.info("getting question of id: " + id);
        return new ResponseEntity<>(service.getQuestionById(id), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Questions> createQuestions(@Valid @RequestBody Questions questions) {
        log.info("creating questions");
        return new ResponseEntity<>(service.createQuestions(questions), HttpStatus.CREATED);
    }

    @PutMapping(path = "")
    public ResponseEntity<Questions> updateQuestions(@RequestBody QuestionDTO question) {
        log.info("updating questions");
        return new ResponseEntity<>(service.updateQuestion(question), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id) {
        if (service.deleteQuestion(id)) {
            return new ResponseEntity<>("deleted question successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
