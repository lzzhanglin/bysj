package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.CourseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseInfoService {

    void createCourseInfo(CourseInfo courseInfo);

    void deleteCourseInfo(@Param("courseInfoId") Long courseInfoId);

    List<CourseInfo> getCourseInfoByType(@Param("infoType") Integer infoType);
}
