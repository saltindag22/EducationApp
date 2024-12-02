package com.example.demo2.models;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class Teacher extends User {
    
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

}
