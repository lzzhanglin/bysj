package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Chapter;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.AttachmentService;
import com.cqjtu.bysj.service.ChapterService;
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
    private AttachmentService attachmentService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Resp createChapter(HttpServletRequest request) {
        String chapterName = request.getParameter("chapterName");
        Integer chapterOrder = Integer.valueOf(request.getParameter("chapterOrder"));
        String creatorJobNo = request.getParameter("creatorJobNo");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        String courseName = request.getParameter("courseName");
        Chapter chapter = new Chapter();
        chapter.setChapterName(chapterName);
        chapter.setChapterOrder(chapterOrder);
        chapter.setCreatorJobNo(creatorJobNo);
        chapter.setCourseId(courseId);
        chapter.setCourseName(courseName);
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
        attachmentService.deleteFileByChapterId(chapterId);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateChapter(HttpServletRequest request) {
        String chapterName = request.getParameter("chapterName");
        String courseName = request.getParameter("courseName");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        Integer chapterOrder = Integer.valueOf(request.getParameter("chapterOrder"));
        Chapter chapter = new Chapter();
        chapter.setCourseName(courseName);
        chapter.setCourseId(courseId);
        chapter.setChapterOrder(chapterOrder);
        chapter.setChapterName(chapterName);
        chapter.setChapterId(chapterId);
        chapterService.updateChapter(chapter);
        return new Resp(RespCode.SUCCESS);

    }
 }
