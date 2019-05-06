package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    void createTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    List<Teacher> getAllTeachers();

    void deleteTeacher(@Param("teacherId") Long teacherId);
}
