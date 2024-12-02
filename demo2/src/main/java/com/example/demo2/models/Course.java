package com.example.demo2.models;

import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name="course")
public class Course {
    
    @Id
    @UuidGenerator
    protected String id;

    private String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Topic> topics;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
 
}
