package com.example.survey.repos;

import com.example.survey.model.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends PagingAndSortingRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);
    List<Users> findByEmailContainingIgnoreCase(String email);
}
