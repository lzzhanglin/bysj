package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.entity.Teacher;
import com.cqjtu.bysj.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Resp createTeacher(HttpServletRequest request) {
        String teacherName = request.getParameter("teacherName");
        String sex = request.getParameter("sex");
        String birthDay = request.getParameter("birthDay");
        String education = request.getParameter("education");
        String jobTitle = request.getParameter("jobTitle");
        String position = request.getParameter("position");
        String affiliatedCollege = request.getParameter("affiliatedCollege");
        String introduction = request.getParameter("introduction");
        Teacher teacher = new Teacher(null, teacherName, sex, birthDay,education, jobTitle, position,affiliatedCollege, introduction);
        teacherService.createTeacher(teacher);
        return new Resp(RespCode.SUCCESS);

    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateTeacher(HttpServletRequest request) {
        Long teacherId = Long.valueOf(request.getParameter("teacherId"));
        String teacherName = request.getParameter("teacherName");
        String sex = request.getParameter("sex");
        String birthDay = request.getParameter("birthDay");
        String education = request.getParameter("education");
        String jobTitle = request.getParameter("jobTitle");
        String position = request.getParameter("position");
        String affiliatedCollege = request.getParameter("affiliatedCollege");
        String introduction = request.getParameter("introduction");
        Teacher teacher = new Teacher(teacherId, teacherName, sex, birthDay, education, jobTitle, position, affiliatedCollege, introduction);
        teacherService.updateTeacher(teacher);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getAllTeachers", method = RequestMethod.GET)
    public Resp getAllTeachers(HttpServletRequest request) {
        List<Teacher> teacherList = teacherService.getAllTeachers();
        return new Resp(RespCode.SUCCESS, teacherList);
    }


    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteTeacher(HttpServletRequest request) {
        Long teacherId = Long.valueOf(request.getParameter("teacherId"));
        teacherService.deleteTeacher(teacherId);
        return new Resp(RespCode.SUCCESS);
    }

}
