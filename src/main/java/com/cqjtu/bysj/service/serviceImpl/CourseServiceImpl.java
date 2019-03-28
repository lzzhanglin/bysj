package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.mapper.CourseMapper;
import com.cqjtu.bysj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service("courseService")

public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseMapper courseMapper;
    public void createCourse(Course course) {
        courseMapper.createCourse(course);
    }
}
