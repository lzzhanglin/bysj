package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.CourseWare;

import java.util.List;

public interface CourseWareService {
    void createCourseWare(CourseWare courseWare);
    List<CourseWare> getCourseWareList(Long chapterId);

    void deleteCourseWare(Long courseWareId);

    void deleteCourseWareByCourseId(Integer courseId);
}
