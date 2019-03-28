package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.AdminUserService;
import com.cqjtu.bysj.service.AuthenticationService;
import com.cqjtu.bysj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminUserService adminUserService;

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
}
