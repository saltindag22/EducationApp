package com.example.demo2.services;

import java.util.List;

import com.example.demo2.models.Question;

public interface QuestionService {
    
    Question createQuestion(String topicId, String questionContent);

    Question getById(String questionId);

    Question updateQuestion(String questionId, String newContent);

    void softDeleteQuestion(String questionId);

    List<Question> getAllQuestions();

       
}
