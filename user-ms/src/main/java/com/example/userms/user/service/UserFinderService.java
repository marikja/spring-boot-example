package com.example.userms.user.service;

import com.example.userms.user.entity.User;
import com.example.userms.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFinderService {

    private final UserRepository userRepository;

    public User getById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
    public Page<User> search(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
