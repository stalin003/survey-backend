package com.example.survey.controller;

import com.example.survey.model.Roles;
import com.example.survey.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "/")
    public ResponseEntity<Roles> createRoles(@RequestBody Roles roles) {
        Roles createRole = roleService.createRoles(roles);
        return new ResponseEntity<>(createRole, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID id) {
        if (roleService.deleteRole(id)) {
            return new ResponseEntity<>("role deleted successfully" , HttpStatus.OK);
        }
        return new ResponseEntity<>("role id not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Roles>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles() , HttpStatus.OK);
    }
}
