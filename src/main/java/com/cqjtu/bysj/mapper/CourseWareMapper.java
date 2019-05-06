package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.CourseWare;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseWareMapper {
    void createCourseWare(CourseWare courseWare);

    List<CourseWare> getCourseWareList(@Param("chapterId") Long chapterId);

    void deleteCourseWare(@Param("courseWareId") Long courseWareId);

    void deleteCourseWareByCourseId(@Param("courseId") Integer courseId);
}
