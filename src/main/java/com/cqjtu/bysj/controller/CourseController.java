package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Course;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.*;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private AttachmentService attachmentService;

//    @RolesAllowed({"ROLE_TEACHER"})

    //后端对方法做权限控制 即使绕过前端 直接访问后端url 没有权限的用户依然无法操作
    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Resp createCourse(HttpServletRequest request) {
        logger.info("角色为：{}",myUserDetailsService.loadUserByUsername(authenticationService.getAuthentication().getName()).getAuthorities()
        );
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
        course.setTeacherJobNo(jobNo);
        courseService.createCourse(course);
        return new Resp(RespCode.SUCCESS);

    }

//    @Secured({"ROLE_TEACHER"})
    @RequestMapping(value = "/getCourseList", method = RequestMethod.GET)
    public Resp<List<Course>> getCourseList(HttpServletRequest request) {
        List<Course> courseList = courseService.getCourseList();
        return new Resp<>(RespCode.SUCCESS, courseList);
    }
    @Secured("ROLE_TEACHER")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateCourse(HttpServletRequest request) {
        String courseName = request.getParameter("courseName");
        String courseIntro = request.getParameter("courseIntro");
        String courseKind = request.getParameter("courseKind");
        String timeBegin = request.getParameter("timeBegin");
        String timeEnd = request.getParameter("timeEnd");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseKind(courseKind);
        course.setCourseName(courseName);
        course.setCourseIntro(courseIntro);
        course.setTimeBegin(timeBegin);
        course.setTimeEnd(timeEnd);
        course.setTeacherId(Long.valueOf(authenticationService.getAuthentication().getName()));
        courseService.updateCourse(course);
        return new Resp(RespCode.SUCCESS);
    }

    @Secured("ROLE_TEACHER")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteCourse(HttpServletRequest request) {
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        courseService.deleteCourse(courseId);
        //删除课程的同时删除章节以及课件
        chapterService.deleteChapterByCourseId(courseId);

        attachmentService.deleteFileByCourseId(courseId);
        return new Resp(RespCode.SUCCESS);
    }
}
