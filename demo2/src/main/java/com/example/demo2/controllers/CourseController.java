package com.example.demo2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.Course;
import com.example.demo2.models.Question;
import com.example.demo2.services.impl.CourseServiceImpl;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    
    @Autowired
    private  CourseServiceImpl courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@PathVariable String courseName) {
        try {
            Course newCourse = courseService.createCourse(courseName);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PutMapping("/update/{courseId}/{courseName}")
    public ResponseEntity<Course> updateQuestion(@PathVariable String courseId, @PathVariable String courseName) {
        try {
            Course updatedCourse = courseService.updateCourse(courseId, courseName);
            return new ResponseEntity<>(updatedCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PostMapping("/delete/{courseId}")
    public ResponseEntity<Question> deleteCourse(@PathVariable String courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
        }
    }

    @GetMapping("/allCourses")
    public ResponseEntity<List<Course>> getAllCourses(){
        try{
            List<Course> allQuestions = courseService.getAllCourses();
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable String courseId){
        try{
            Course course = courseService.getById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }
    }

    @PostMapping("/{courseId}/assignTeacher/{userId}")
    public ResponseEntity<String> assignTeacher(@PathVariable String courseId, @PathVariable String userId) {
        try{
            courseService.assignTeacher(courseId, userId);
            return new ResponseEntity<>("Teacher is assigned succesfully", HttpStatus.OK);
        }
        catch (ResourceAccessException e) {
            return new ResponseEntity<>("Course or Teacher not found", HttpStatus.NOT_FOUND);  
        }catch (SecurityException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);  
        }catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR); 
    }

}
    @PostMapping("/{courseId}/addTopic/{topicId}")
    public ResponseEntity<String> addTopicToCourse(@PathVariable String courseId, @PathVariable String topicId){
        try{
            courseService.addTopicToCourse(courseId, topicId);
            return new ResponseEntity<>("Topic is added to course succesfully", HttpStatus.OK);
        }
        catch (ResourceAccessException e) {
            return new ResponseEntity<>("Course or Topic not found", HttpStatus.NOT_FOUND); 
        }catch (SecurityException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);  
        }catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR); 
    }

    }

       

    

}
