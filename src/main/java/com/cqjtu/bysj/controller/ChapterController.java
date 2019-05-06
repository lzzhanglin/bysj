package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Chapter;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.ChapterService;
import com.cqjtu.bysj.service.CourseService;
import com.cqjtu.bysj.service.CourseWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private CourseWareService courseWareService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Resp createChapter(HttpServletRequest request) {
        String chapterName = request.getParameter("chapterName");
        Integer chapterOrder = Integer.valueOf(request.getParameter("chapterOrder"));
        String chapterContent = request.getParameter("chapterContent");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Chapter chapter = new Chapter();
        chapter.setChapterName(chapterName);
        chapter.setChapterOrder(chapterOrder);
        chapter.setChapterContent(chapterContent);
        chapter.setCreatorJobNo(creatorJobNo);
        chapter.setCourseId(courseId);
        Long chapterId =  chapterService.createChapter(chapter);
        return new Resp(RespCode.SUCCESS,chapterId);
    }

    @RequestMapping(value = "getChapterList", method = RequestMethod.GET)
    public Resp getChapterList(HttpServletRequest request) {
        String cId = request.getParameter("courseId");
        if (Objects.equals(cId, null)) {
            return new Resp(RespCode.SUCCESS);
        } else {


        Integer courseId = Integer.valueOf(cId);
        List<Chapter> chapterList = chapterService.getChapterList(courseId);
        return new Resp(RespCode.SUCCESS, chapterList);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteChapter(HttpServletRequest request) {
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        chapterService.deleteChapter(chapterId);
        //删除章节的同时删除其附带的课件
        courseWareService.deleteCourseWare(chapterId);
        return new Resp(RespCode.SUCCESS);
    }
 }
