package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Course;

import java.util.List;

public interface CourseService {



    void createCourse(Course course);

    List<Course> getCourseList(String jobNo);

    void updateCourse(Course course);

    void deleteCourse(Integer courseId);

}
