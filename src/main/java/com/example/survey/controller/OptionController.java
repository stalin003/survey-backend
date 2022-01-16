package com.example.survey.controller;

import com.example.survey.model.Options;
import com.example.survey.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping(path = "")
    public ResponseEntity<List<Options>> getAllOptions() {
        log.info("getting all options");
        return new ResponseEntity<>(optionService.getAllOptions(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Options> getOptionById(@PathVariable int id) {
        log.info("getting option of id: " + id);
        return new ResponseEntity<>(optionService.findOptionById(id), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Options> createOptions(@RequestBody Options options) {
        return new ResponseEntity<>(optionService.createOption(options), HttpStatus.CREATED);
    }

    @PutMapping(path = "")
    public ResponseEntity<Options> updateOptions(@RequestBody Options options) {
        return new ResponseEntity<>(optionService.updateOption(options), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteOption(@PathVariable int id) {
        if (optionService.deleteOption(id)) {
            return new ResponseEntity<>("deleted option successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
    }


}
