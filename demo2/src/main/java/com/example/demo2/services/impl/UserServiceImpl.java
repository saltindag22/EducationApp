package com.example.demo2.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo2.models.User;
import com.example.demo2.repositories.UserRepo;
import com.example.demo2.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    

      @Autowired
      private final UserRepo userRepo;


      @Override
      public User getByEmail(String email) {
         return userRepo.findByEmail(email).orElse(null);
      }

      @Override
      public User getById(String userId) {
         return userRepo.findById(userId).orElse(null);
      }

      @Override
      @PreAuthorize("hasRole('ADMIN')")
      public List<User> getAllUsers() {
         return userRepo.findAll();
      }

    
    
}
