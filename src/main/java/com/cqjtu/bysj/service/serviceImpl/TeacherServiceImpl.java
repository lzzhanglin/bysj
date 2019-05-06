package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Teacher;
import com.cqjtu.bysj.mapper.TeacherMapper;
import com.cqjtu.bysj.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void createTeacher(Teacher teacher) {
        teacherMapper.createTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherMapper.updateTeacher(teacher);
    }
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.getAllTeachers();
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherMapper.deleteTeacher(teacherId);
    }

}
