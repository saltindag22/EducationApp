package com.example.demo2.services;

import com.example.demo2.models.Question;

public interface AiService {
    
    Question generateAiQuestion(String topic);
}
