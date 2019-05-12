package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.CourseWare;
import com.cqjtu.bysj.mapper.CourseWareMapper;
import com.cqjtu.bysj.service.CourseWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service("courseWareService")
public class CourseWareServiceImpl implements CourseWareService {


    @Autowired
    private CourseWareMapper courseWareMapper;

    @Override
    public void createCourseWare(CourseWare courseWare) {
        courseWareMapper.createCourseWare(courseWare);
    }

    @Override
    public List<CourseWare> getCourseWareList(Long chapterId, Integer wareType) {
        return courseWareMapper.getCourseWareList(chapterId,wareType);
    }

    @Override
    public List<CourseWare> getCourseWareListByCourseId(Integer courseId, Integer wareType){
        return courseWareMapper.getCourseWareListByCourseId(courseId, wareType);
    }

    @Override
    public void deleteCourseWare(Long courseWareId){
        courseWareMapper.deleteCourseWare(courseWareId);
    }


    @Override
    public void deleteCourseWareByCourseId(Integer courseId){
        courseWareMapper.deleteCourseWareByCourseId(courseId);
    }

}
