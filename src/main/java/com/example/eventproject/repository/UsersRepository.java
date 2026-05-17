package com.example.eventproject.repository;

import com.example.eventproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailEqualsIgnoreCase(String email);
    boolean existsByEmailEqualsIgnoreCase(String email);
}