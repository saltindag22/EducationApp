package com.example.demo2.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo2.config.AiConfiguration;
import com.example.demo2.models.Question;
import com.example.demo2.models.Topic;
import com.example.demo2.repositories.QuestionRepo;
import com.example.demo2.repositories.TopicRepo;
import com.example.demo2.services.AiService;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private  AiConfiguration aiConfiguration;

    @Value("${flask.api.url}")
    private String flaskApiUrl;


    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private QuestionRepo questionRepo;

    
    @Override
    public Question generateAiQuestion(String topic) {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(flaskApiUrl)
                .path("/generate-question")
                .queryParam("topic", topic)
                .toUriString();

        String newQuestionContent = aiConfiguration.restTemplate().getForObject(apiUrl, String.class);
        Question question = new Question();
        Topic aimTopic = topicRepo.findByName(topic)
                .orElseThrow(() -> new ResourceAccessException("Topic not found with id: " + topic));

        question.setAiGenerated(true);
        question.setContent(newQuestionContent);
        question.setTopic(aimTopic);

        return questionRepo.save(question);
    }
    
}

