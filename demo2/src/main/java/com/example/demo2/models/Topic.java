package com.example.demo2.models;

import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
@Getter
@Setter
@Table(name="topic")
public class Topic {
    
    @Id
    @UuidGenerator
    protected String id;

    private String name;

    @OneToMany(mappedBy = "topic", cascade= CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Question> questions;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

}
