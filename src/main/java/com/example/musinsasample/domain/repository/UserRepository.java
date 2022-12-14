package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, InternalError> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
