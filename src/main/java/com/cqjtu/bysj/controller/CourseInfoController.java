package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.CourseInfo;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseInfo")
public class CourseInfoController {

    @Autowired
    private CourseInfoService courseInfoService;

    @RequestMapping(value = "create")
    public Resp createCourseInfo(HttpServletRequest request) {
        String infoName = request.getParameter("infoName");
        String infoDetail = request.getParameter("infoDetail");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Integer infoType = Integer.valueOf(request.getParameter("infoType"));
        CourseInfo courseInfo = new CourseInfo(creatorJobNo, infoName, infoDetail, infoType);
        courseInfoService.createCourseInfo(courseInfo);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getCourseInfoByType")
    public Resp getCourseInfoByType(HttpServletRequest request) {
        Integer infoType = Integer.valueOf(request.getParameter("infoType"));
        List<CourseInfo> courseInfos = courseInfoService.getCourseInfoByType(infoType);
        return new Resp(RespCode.SUCCESS, courseInfos);
    }

    @RequestMapping(value = "delete")
    public Resp deleteCourseInfo(HttpServletRequest request) {
        Long courseInfoId = Long.valueOf(request.getParameter("courseInfoId"));
        courseInfoService.deleteCourseInfo(courseInfoId);
        return new Resp(RespCode.SUCCESS);
    }

}
