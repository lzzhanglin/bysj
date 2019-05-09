package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.CourseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseInfoMapper {

    void createCourseInfo(CourseInfo courseInfo);

    void deleteCourseInfo(@Param("courseInfoId") Long courseInfoId);

    List<CourseInfo> getCourseInfoByType(@Param("infoType") Integer infoType);
}
