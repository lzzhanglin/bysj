package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void createTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    List<Teacher> getAllTeachers();

    void deleteTeacher(Long teacherId);
}
