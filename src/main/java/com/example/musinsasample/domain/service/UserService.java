package com.example.musinsasample.domain.service;

import com.example.musinsasample.domain.entity.User;
import com.example.musinsasample.domain.repository.UserRepository;
import com.example.musinsasample.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }

        User user = new User(username);
        userRepository.save(user);
    }
}
