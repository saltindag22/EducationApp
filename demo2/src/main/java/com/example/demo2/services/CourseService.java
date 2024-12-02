package com.example.demo2.services;

import java.util.List;

import com.example.demo2.models.Course;

public interface CourseService {

    Course createCourse(String courseName);

    Course updateCourse(String courseId, String newCourseName);

    void deleteCourse(String courseId);

    List<Course> getAllCourses();

    Course getById(String courseId);

    void assignTeacher(String courseId,String userId);

    void addTopicToCourse(String courseId,String topicId);
}
