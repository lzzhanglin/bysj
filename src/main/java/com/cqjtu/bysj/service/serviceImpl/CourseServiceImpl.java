package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.mapper.CourseMapper;
import com.cqjtu.bysj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service("courseService")

public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseMapper courseMapper;
    //启用事务
    @Override
    @Transactional
    public void createCourse(Course course) {
        courseMapper.createCourse(course);
    }
    @Override
    @Transactional
    public List<Course> getCourseList(String jobNo) {
        return courseMapper.getCourseList(jobNo);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Integer courseId) {
        courseMapper.deleteCourse(courseId);
    }
}
