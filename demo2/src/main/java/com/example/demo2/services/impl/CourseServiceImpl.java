package com.example.demo2.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.demo2.models.Course;
import com.example.demo2.models.Roles;
import com.example.demo2.models.Teacher;
import com.example.demo2.models.Topic;
import com.example.demo2.models.User;
import com.example.demo2.repositories.CourseRepo;
import com.example.demo2.repositories.TopicRepo;
import com.example.demo2.repositories.UserRepo;
import com.example.demo2.services.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Course createCourse(String courseName) {

        Course newCourse = new Course();
        newCourse.setName(courseName);

        return courseRepo.save(newCourse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Course updateCourse(String courseId, String newCourseName) {
        Course course = courseRepo.findById(courseId)
        .orElseThrow(() -> new ResourceAccessException("Course not found with id: " + courseId));

        course.setName(newCourseName);
        return courseRepo.save(course);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(String courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceAccessException("Course not found with id: " + courseId));

        courseRepo.delete(course);
    }

    @Override
    public List<Course> getAllCourses() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceAccessException("Course not found with id: " + username));

        if (user.getRole()== Roles.ADMIN){
            return courseRepo.findAll();
        }
       return ((Teacher) user).getCourses();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void assignTeacher(String courseId,String userId) {
       
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceAccessException("Course not found with id: " + courseId));

        User teacher = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceAccessException("User not found with id: " + userId));

        ((Teacher) teacher).getCourses().add(course);
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void addTopicToCourse(String courseId,String topicId) {
       
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceAccessException("Course not found with id: " + courseId));

        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new ResourceAccessException("Topic not found with id: " + topicId));

        course.getTopics().add(topic);
    }

    @Override
    public Course getById(String courseId) {
       return courseRepo.findById(courseId).orElse(null);
    }

}
