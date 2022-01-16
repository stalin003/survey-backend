package com.example.survey;

import com.example.survey.model.Roles;
import com.example.survey.model.Users;
import com.example.survey.repos.UserRepo;
import com.example.survey.util.UserRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/login").allowedOrigins("*");
            }
        };
    }

    @Bean
    public CommandLineRunner demoData(UserRepo repo) {

        Users users = new Users();

        Roles roles = new Roles();
        roles.setRoles(UserRoles.ROLE_OFFICER);
        roles.setUsers(users);

        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(roles);

        users.setEmail("admin@shangrila.gov.un");
        users.setFullName("admin");
        users.setDOB("NIL");
        users.setHomeAddress("NIL");
        users.setSNI("NIL");
        users.setPassword(this.bCryptPasswordEncoder().encode("shangrila@2021$"));
        users.setActive(true);
        users.setRoles(rolesList);

        Optional<Users> u = repo.findByEmail("admin@shangrila.gov.un");

        return args -> {

            if (u.isEmpty()) {
                repo.save(users);
            }

        };
    }

}
