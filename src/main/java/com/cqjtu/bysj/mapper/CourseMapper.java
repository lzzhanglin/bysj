package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    void createCourse(Course course);

    List<Course> getCourseList(@Param("jobNo") String jobNo);

    void updateCourse(Course course);
}
