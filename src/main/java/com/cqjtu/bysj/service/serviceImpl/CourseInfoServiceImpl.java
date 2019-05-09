package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.CourseInfo;
import com.cqjtu.bysj.mapper.CourseInfoMapper;
import com.cqjtu.bysj.service.CourseInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseInfoService")
public class CourseInfoServiceImpl implements CourseInfoService {


    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Override
    public void createCourseInfo(CourseInfo courseInfo){
        courseInfoMapper.createCourseInfo(courseInfo);
    }

    @Override
    public void deleteCourseInfo(@Param("courseInfoId") Long courseInfoId){
        courseInfoMapper.deleteCourseInfo(courseInfoId);
    }

    @Override
    public List<CourseInfo> getCourseInfoByType(@Param("infoType") Integer infoType) {
        return courseInfoMapper.getCourseInfoByType(infoType);
    }
}
