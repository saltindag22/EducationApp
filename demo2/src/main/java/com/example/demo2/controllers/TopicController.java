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

import com.example.demo2.models.Topic;
import com.example.demo2.services.impl.TopicServiceImpl;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    
    @Autowired
    private TopicServiceImpl topicService;



    @PostMapping("/create")
    public ResponseEntity<Topic> createCourse(@PathVariable String courseId, @PathVariable String topicName) {
        try {
            Topic newTopic = topicService.createTopic(courseId, topicName);
            return new ResponseEntity<>(newTopic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PutMapping("/update/{topicId}")
    public ResponseEntity<Topic> updateQuestion(@PathVariable String topicId, @PathVariable String newTopicName) {
        try {
            Topic updatedTopic = topicService.updateTopic(topicId, newTopicName);
            return new ResponseEntity<>(updatedTopic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PostMapping("/delete/{topicId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String topicId) {
        try {
            topicService.deleteTopic(topicId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 No Content on success
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if the question is not found
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Return 403 if permission is denied
        }
    }


    @GetMapping("/allTopics")
    public ResponseEntity<List<Topic>> getAllTopics(){
        try{
            List<Topic> allTopics = topicService.getAllTopics();
            return new ResponseEntity<>(allTopics, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }
    }
    @GetMapping("/{topicId}")
    public ResponseEntity<Topic> getById(@PathVariable String topicId){
        try{
            Topic topic = topicService.getById(topicId);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }
    }
    

}
