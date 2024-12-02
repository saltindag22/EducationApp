package com.example.demo2.services;

import java.util.List;

import com.example.demo2.models.Topic;

public interface TopicService {
    
    Topic createTopic(String courseId, String topicName);

    Topic updateTopic(String topicId, String newTopicName);

    void deleteTopic(String topicId);

    List<Topic> getAllTopics();

    Topic getById(String topicId);


}
