package com.example.userms.user.service;

import com.example.userms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
