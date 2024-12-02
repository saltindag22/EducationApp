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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.Question;
import com.example.demo2.services.impl.AiServiceImpl;
import com.example.demo2.services.impl.QuestionServiceImpl;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    
    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private AiServiceImpl aiService;

    @PostMapping("/create/{topicId}")
    public ResponseEntity<Question> createQuestion(@PathVariable String topicId, @RequestParam String questionContent) {
        try {
            Question newQuestion = questionService.createQuestion(topicId, questionContent);
            return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String questionId, @RequestParam String newContent) {
        try {
            Question updatedQuestion = questionService.updateQuestion(questionId, newContent);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }

    @PostMapping("/softDelete/{questionId}")
    public ResponseEntity<Question> softDeleteQuestion(@PathVariable String questionId) {
        try {
            questionService.softDeleteQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
        }
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            List<Question> allQuestions = questionService.getAllQuestions();
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        }
        catch(SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
    }
    }

    @GetMapping("/questionById{questionId}")
    public ResponseEntity<Question> getById(@PathVariable String questionId){
        try{
            Question question = questionService.getById(questionId);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }
        catch (ResourceAccessException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    @PostMapping("/api/generate-question/{topic}")
    public ResponseEntity<Question> generateQuestion(@PathVariable String topic) {
        try {
            Question newQuestion = aiService.generateAiQuestion(topic);
            return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }
}
