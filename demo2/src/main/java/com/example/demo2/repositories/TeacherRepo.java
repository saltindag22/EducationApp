package com.example.demo2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo2.models.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher,String> {
    
}
