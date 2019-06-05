package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Attachment;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.qiniu.QiniuUpload;
import com.cqjtu.bysj.service.AttachmentService;
import com.cqjtu.bysj.util.GetRandomFileName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "getToken", method = RequestMethod.GET)
    public Resp getToken(HttpServletRequest request) {
        String fileName = request.getParameter("fileName");
        String bucketName = request.getParameter("bucketName");
        GetRandomFileName getRandomFileName = new GetRandomFileName();
        String newFileName = getRandomFileName.getRandomFileName(fileName,10);
        QiniuUpload upload = new QiniuUpload();
        String token = upload.getToken(bucketName);
        List<String> infoList = new ArrayList<>();
        infoList.add(token);
        infoList.add(newFileName);
        return new Resp(RespCode.SUCCESS, infoList);
    }
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Resp upload(HttpServletRequest request) {
        String attachName = request.getParameter("attachName");
        String creatorJobNo = request.getParameter("creatorJobNo");
        String fileType = request.getParameter("fileType");
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");
        String courseIdStr = request.getParameter("courseId");
        String chapterIdStr = request.getParameter("chapterId");
        String describe = request.getParameter("describe");
        Attachment attachment = new Attachment();
        attachment.setAttachName(attachName);
        attachment.setFileType(fileType);
        attachment.setCreatorJobNo(creatorJobNo);
        attachment.setExternalLink(externalLink);
        attachment.setPreviewLink(previewLink);
        attachment.setDescribe(describe);
        if (!Objects.equals(courseIdStr, null)) {
            attachment.setCourseId(Long.valueOf(courseIdStr));
        }
        if (!Objects.equals(chapterIdStr, null)) {
            attachment.setChapterId(Long.valueOf(chapterIdStr));
        }
        attachmentService.uploadFile(attachment);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getFileByType", method = RequestMethod.GET)
    public Resp getFileByType(HttpServletRequest request) {
        String fileType = request.getParameter("fileType");
        return new Resp(RespCode.SUCCESS,attachmentService.getFileByType(fileType));
    }

    @RequestMapping(value = "getFileByDoubleIdAndType", method = RequestMethod.GET)
    public Resp getFileByDoubleIdAndType(HttpServletRequest request) {
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        String fileType = request.getParameter("fileType");
        return new Resp(RespCode.SUCCESS, attachmentService.getFileByDoubleIdAndType(courseId, chapterId, fileType));
    }

    @RequestMapping(value = "getFileByCourseIdAndType", method = RequestMethod.GET)
    public Resp getFileByCourseIdAndType(HttpServletRequest request) {
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        String fileType = request.getParameter("fileType");
        return new Resp(RespCode.SUCCESS, attachmentService.getFileByCourseIdAndType(courseId, fileType));
    }

    @RequestMapping(value = "deleteFileById", method = RequestMethod.DELETE)
    public Resp deleteFileById(HttpServletRequest request) {
        Long attachId = Long.valueOf(request.getParameter("attachId"));
        attachmentService.deleteFileById(attachId);
        return new Resp(RespCode.SUCCESS);
    }
    @RequestMapping(value = "deleteFileByCourseId", method = RequestMethod.DELETE)
    public Resp deleteFileByCourseId(HttpServletRequest request) {
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        attachmentService.deleteFileByCourseId(courseId);
        return new Resp(RespCode.SUCCESS);
    }
}
