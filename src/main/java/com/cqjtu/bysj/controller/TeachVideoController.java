package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.entity.TeachVideo;
import com.cqjtu.bysj.service.TeachVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teachVideo")
public class TeachVideoController {

    @Autowired
    private TeachVideoService videoService;

    @RequestMapping(value = "writeFileInfo", method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {
        String videoName = request.getParameter("videoName");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");
        TeachVideo video = new TeachVideo();
        video.setChapterId(chapterId);
        video.setCourseId(courseId);
        video.setCreatorJobNo(creatorJobNo);
        video.setVideoName(videoName);
        video.setExternalLink(externalLink);
        video.setPreviewLink(previewLink);
        videoService.createTeachVideo(video);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getTeachVideoList", method = RequestMethod.GET)
    public Resp getTeachVideoList(HttpServletRequest request) {
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        List<TeachVideo> videoList = videoService.getVideoList(chapterId);
        return new Resp(RespCode.SUCCESS, videoList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteVideo(HttpServletRequest request) {
        Long videoId = Long.valueOf(request.getParameter("videoId"));
        videoService.deleteTeachVideo(videoId);
        return new Resp(RespCode.SUCCESS);
    }

}
