package com.example.userms.module.user.service;

import com.example.userms.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
