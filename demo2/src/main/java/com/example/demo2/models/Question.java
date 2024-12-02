package com.example.demo2.models;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name="question")
public class Question {
    
    @Id
    @UuidGenerator
    protected String id;

    private String content;


    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false; 

    @Column(name = "is_Ai_Generated", nullable = false)
    private boolean aiGenerated = false; 

}
