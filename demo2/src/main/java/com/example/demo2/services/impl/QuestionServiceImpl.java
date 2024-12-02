package com.example.demo2.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.Question;
import com.example.demo2.models.Topic;
import com.example.demo2.models.User;
import com.example.demo2.repositories.QuestionRepo;
import com.example.demo2.repositories.TopicRepo;
import com.example.demo2.repositories.UserRepo;
import com.example.demo2.services.QuestionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QuestionRepo questionRepo;
    
    @Autowired
    private TopicRepo topicRepo;


    @Override
    @PreAuthorize("permitAll()")
    public Question createQuestion(String topicId, String questionContent) {
        Question question = new Question();
        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new ResourceAccessException("Topic not found with id: " + topicId));

          UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceAccessException("User not found with id: " + username));

        question.setContent(questionContent);
        question.setTopic(topic);
        question.setUser(user);
        return questionRepo.save(question);

        
    }

    @Override
    @PreAuthorize("permitAll()")
    public Question updateQuestion(String questionId, String newContent) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new ResourceAccessException("Question not found with id: " + questionId));
    
       question.setContent(newContent);
       return questionRepo.save(question);
    }

    @Override
    @PreAuthorize("permitAll()")
    public void softDeleteQuestion(String questionId) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new ResourceAccessException("Question not found with id: " + questionId));
        
        question.setDeleted(true);
        questionRepo.save(question);

    }

    @Override
    @PreAuthorize("permitAll()")
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }


    @Override
    @PreAuthorize("permitAll()")
    public Question getById(String questionId) {
        return questionRepo.findById(questionId).orElse(null);
    }

}