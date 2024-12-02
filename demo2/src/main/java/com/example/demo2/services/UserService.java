package com.example.demo2.services;

import java.util.List;

import com.example.demo2.models.User;


public interface UserService {


    User getById(String userId);

    User getByEmail(String email);

    List<User> getAllUsers();
    
}
