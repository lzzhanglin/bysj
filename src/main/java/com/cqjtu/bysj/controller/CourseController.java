package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.AdminUserService;
import com.cqjtu.bysj.service.AuthenticationService;
import com.cqjtu.bysj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController

@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminUserService adminUserService;

    @RolesAllowed({"TEACHER"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Resp createCourse(HttpServletRequest request) {
        String courseName = request.getParameter("courseName");
        String courseKind = request.getParameter("courseKind");
        String timeBegin = request.getParameter("timeBegin");
        String timeEnd = request.getParameter("timeEnd");
        String courseIntro = request.getParameter("courseIntro");
        String jobNo = authenticationService.getAuthentication().getName();
        UserDetails userDetails = adminUserService.getUserInfoByJobNo(jobNo);
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseKind(courseKind);
        course.setTimeBegin(timeBegin);
        course.setTimeEnd(timeEnd);
        course.setCourseIntro(courseIntro);
        course.setTeacherId(((AdminUser) userDetails).getUserId());
        course.setTeacherName(userDetails.getUsername());
        courseService.createCourse(course);
        return new Resp(RespCode.SUCCESS);

    }

    @RequestMapping(value = "/getCourseList", method = RequestMethod.GET)
    public Resp<List<Course>> getCourseList(HttpServletRequest request) {
        String jobNo = request.getParameter("jobNo");
        String jobNo1 = "1";
        List<Course> courseList = courseService.getCourseList(jobNo1);
        return new Resp<>(RespCode.SUCCESS, courseList);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateCourse(HttpServletRequest request) {
        String courseName = request.getParameter("courseName");
        String courseIntro = request.getParameter("courseIntro");
        String timeBegin = request.getParameter("timeBegin");
        String timeEnd = request.getParameter("timeEnd");
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseIntro(courseIntro);
        course.setTimeBegin(timeBegin);
        course.setTimeEnd(timeEnd);
        course.setTeacherId(Long.valueOf(authenticationService.getAuthentication().getName()));
        courseService.updateCourse(course);
        return new Resp(RespCode.SUCCESS);
    }
}
