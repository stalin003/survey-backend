package com.example.survey.controller;

import com.example.survey.DTO.AnsweredQuestionDTO;
import com.example.survey.DTO.OptionVotesDTO;
import com.example.survey.DTO.ResidentRespondDTO;
import com.example.survey.model.AnsweredQuestions;
import com.example.survey.service.AQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/AQ")
public class AQController {

    @Autowired
    private AQService service;

    @PostMapping(path = "")
    public ResponseEntity<AnsweredQuestions> createAQ(@RequestBody AnsweredQuestionDTO AQ) {
        if (service.createAQ(AQ) != null) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = "")
    public ResponseEntity<List<AnsweredQuestions>> getAllAQ() {
        return new ResponseEntity<>(service.getAllAQ(), HttpStatus.OK);
    }

    @GetMapping(path = "response")
    public ResponseEntity<List<ResidentRespondDTO>> getResidentResponseCount() {
        return new ResponseEntity<>(service.getCountOfResidentResponse(), HttpStatus.OK);
    }

    @GetMapping(path = "votes/{id}")
    public ResponseEntity<List<OptionVotesDTO>> getOptionVotes(@PathVariable int id) {
        return new ResponseEntity<>(service.getOptionVotes(String.valueOf(id)), HttpStatus.OK);
    }



}
