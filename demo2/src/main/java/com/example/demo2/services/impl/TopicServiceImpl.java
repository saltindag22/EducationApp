package com.example.demo2.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.Topic;
import com.example.demo2.models.User;
import com.example.demo2.repositories.TopicRepo;
import com.example.demo2.repositories.UserRepo;
import com.example.demo2.services.TopicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService{

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private TopicRepo topicRepo;

    @Override
    public Topic createTopic(String courseId, String topicName) {
         UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceAccessException("User not found with id: " + username));
        Topic newTopic = new Topic();
        newTopic.setName(topicName);
        newTopic.setCreatedBy(user);
        return topicRepo.save(newTopic);
       
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Topic updateTopic(String topicId, String newTopicName) {
        Topic topic = topicRepo.findById(topicId)
        .orElseThrow(() -> new ResourceAccessException("Topic not found with id: " + topicId));

        topic.setName(newTopicName);
        return topicRepo.save(topic);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTopic(String topicId) {
        Topic topic = topicRepo.findById(topicId)
        .orElseThrow(() -> new ResourceAccessException("Topic not found with id: " + topicId));

        topicRepo.delete(topic);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Topic> getAllTopics() {
        return topicRepo.findAll();
    }

    @Override
    public Topic getById (String topicId) {
        return topicRepo.findById(topicId).orElse(null);
    }
   
    
}
