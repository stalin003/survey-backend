package com.example.survey.controller;

import com.example.survey.DTO.DisableDTO;
import com.example.survey.DTO.TotalPagesDTO;
import com.example.survey.model.Users;
import com.example.survey.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@Validated
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<List<Users>> getAllUser(@RequestParam int page, @RequestParam int size) {
        log.info("getting all users");
        return new ResponseEntity<>(userService.getAllUsers(page, size), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users users) {

        Users createdUsers = userService.createUsers(users);
        return new ResponseEntity<>(createdUsers, HttpStatus.CREATED);
    }

    @PutMapping(path = "")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody Users users) {
        Users updatedUser = userService.updateUsers(users);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "disable")
    public ResponseEntity<Object> disableUser(@RequestBody DisableDTO disableDTO) {
        if (userService.disableUser(disableDTO)) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        Users users = userService.getUserById(id);

        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "name/{name}")
    public ResponseEntity<List<Users>> getUserLikeName(@PathVariable String name) {
        List<Users> users = userService.getUsersLikeName(name);

        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id ) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "pages")
    public ResponseEntity<TotalPagesDTO> getProductTotalPages() {
        return new ResponseEntity<>(this.userService.getUsersTotalPages(), HttpStatus.OK);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
