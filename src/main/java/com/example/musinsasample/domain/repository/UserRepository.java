package com.example.musinsasample.domain.repository;

import com.example.musinsasample.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, InternalError> {
    boolean existsByUsername(String username);
}
