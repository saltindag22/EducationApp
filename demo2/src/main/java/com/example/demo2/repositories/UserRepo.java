package com.example.demo2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.models.Roles;
import com.example.demo2.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);

    User findByRole(Roles role);
}