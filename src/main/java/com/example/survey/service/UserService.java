package com.example.survey.service;

import com.example.survey.DTO.DisableDTO;
import com.example.survey.DTO.TotalPagesDTO;
import com.example.survey.model.Users;
import com.example.survey.repos.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users createUsers(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        log.debug("password getting encrypted");
        log.info("new user has been created");

        return userRepo.save(users);
    }

    public List<Users> getAllUsers(Integer pgNo, Integer pgSize) {

        Pageable pageable = PageRequest.of(pgNo, pgSize);

        Page<Users> pageResult = userRepo.findAll(pageable);

        if (pageResult.hasContent()) {
            return pageResult.getContent();
        }

        return null;
    }

    public Users getUserByName(String name) throws UsernameNotFoundException {
        Optional<Users> users = userRepo.findByEmail(name);
        users.orElseThrow(() -> {
            log.error("username not found");
            return new UsernameNotFoundException("User name not found: " + name);
        });
        log.info("retrieving user by name");
        return users.get();
    }

    public Users getUserById(UUID id) {
        Optional<Users> users = userRepo.findById(id);

        if (users.isPresent()) {

            log.info("retrieving user by id");
            return users.get();
        }
        log.info("user not found");

        return null;
    }

    public boolean deleteUser(UUID id) {

        Users users = getUserById(id);

        if(users != null) {
            userRepo.delete(users);
            log.info("user {} deleted successfully", users.getEmail());
            return true;
        }

        log.error("user id: {} not found", id);

        return false;
    }

    public Users updateUsers(Users users) {
        Users getUsers = getUserById(users.getId());

        if (getUsers != null) {
            log.info("updating user {}", users.getId());
            users.setPassword(getUsers.getPassword());
            users.setCreatedAt(getUsers.getCreatedAt());
            return userRepo.save(users);
        }

        log.info("updating user {} failed", users.getId());
        return null;
    }

    public TotalPagesDTO getUsersTotalPages() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Users> bills = userRepo.findAll(pageable);
        return new TotalPagesDTO(bills.getTotalPages());
    }


    public List<Users> getUsersLikeName(String name) {
        List<Users> users = userRepo.findByEmailContainingIgnoreCase(name);

        if (users.size() > 0) {
            log.info("retrieving users containing name like {}", name);
            return users;
        }

        log.info("users is empty");
        return null;
    }

    public boolean disableUser(DisableDTO disableDTO) {
        Users users = this.getUserById(disableDTO.getId());

        if (users == null) {
            return false;
        }

        users.setActive(!disableDTO.isDisable());
        userRepo.save(users);

        return true;
    }
}
