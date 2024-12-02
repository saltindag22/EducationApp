package com.example.demo2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.models.Topic;

@Repository
public interface TopicRepo extends JpaRepository<Topic,String> {

    Optional<Topic> findByName(String topicName);
    
}
