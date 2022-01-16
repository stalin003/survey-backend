package com.example.survey.service;


import com.example.survey.model.Roles;
import com.example.survey.repos.RolesRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class RoleService {

    @Autowired
    RolesRepo rolesRepo;

    public Roles createRoles(Roles roles) {

        log.info("A new roles has been create");
        return rolesRepo.save(roles);
    }

    public Roles getRoleById(UUID id) {

        Optional<Roles> roles = rolesRepo.findById(id);

        if (roles.isPresent()) {
            log.info("retrieving role of id: {}", id);
            return roles.get();
        }

        log.info("unable to retrieve role of id: {}", id);
        return null;
    }

    public boolean deleteRole(UUID id) {
        Roles roles = getRoleById(id);

        if (roles != null) {
            log.info("deleting role of id: {}", id);
            rolesRepo.delete(roles);

            log.info("deleted of role id: {}", id);
            return true;
        }

        log.info("failed to deleted role of id: {}", id);

        return false;
    }

    public List<Roles> createBunchOfRoles(List<Roles> roles) {
        log.info("bunch of roles created");
        return rolesRepo.saveAll(roles);
    }

    public List<Roles> getAllRoles() {
        log.info("retrieving all roles");
        return rolesRepo.findAll();
    }

}
