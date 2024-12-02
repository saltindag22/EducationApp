package com.example.demo2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.User;
import com.example.demo2.services.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable String userId){
        try{
            User user = userService.getById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (ResourceAccessException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
}

@GetMapping("/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email){
        try{
            User user = userService.getByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (ResourceAccessException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
}

@GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllTopics(){
        try{
            List<User> allUsers = userService.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }

}
}
