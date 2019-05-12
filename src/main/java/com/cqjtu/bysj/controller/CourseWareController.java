package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.CourseWare;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.CourseWareService;
import com.cqjtu.bysj.util.GetRandomFileName;
import com.cqjtu.bysj.util.MediaTypeUtils;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/courseWare")
public class CourseWareController {

    @Autowired
    private CourseWareService courseWareService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "writeFileInfo",method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {

        String fileName = request.getParameter("courseWareName");
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");

        String chapterIdStr = request.getParameter("chapterId");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Integer wareType = Integer.valueOf(request.getParameter("wareType"));
        CourseWare courseWare = new CourseWare();
        if (!Objects.equals(chapterIdStr, null)) {
            courseWare.setChapterId(Long.valueOf(chapterIdStr));
        }
        courseWare.setCourseWareName(fileName);
        courseWare.setCreatorJobNo(creatorJobNo);
        courseWare.setCourseId(courseId);
        courseWare.setExternalLink(externalLink);
        courseWare.setPreviewLink(previewLink);
        courseWare.setWareType(wareType);
        courseWareService.createCourseWare(courseWare);


            return new Resp(RespCode.SUCCESS);

    }

    @RequestMapping(value = "getCourseWareList",method = RequestMethod.GET)
    public Resp getCourseWareList(HttpServletRequest request) {
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        Integer wareType = Integer.valueOf(request.getParameter("wareType"));
        List<CourseWare> courseWareList = courseWareService.getCourseWareList(chapterId, wareType);
        return new Resp(RespCode.SUCCESS, courseWareList);
    }
    @RequestMapping(value = "getCourseWareListByCourseId",method = RequestMethod.GET)
    public Resp getCourseWareListByCourseId(HttpServletRequest request) {
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Integer wareType = Integer.valueOf(request.getParameter("wareType"));
        List<CourseWare> courseWareList = courseWareService.getCourseWareListByCourseId(courseId, wareType);
        return new Resp(RespCode.SUCCESS, courseWareList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteCourseWare(HttpServletRequest request) {
        Long courseWareId = Long.valueOf(request.getParameter("courseWareId"));
        courseWareService.deleteCourseWare(courseWareId);
        return new Resp(RespCode.SUCCESS);
    }



    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getDownload(HttpServletRequest request, HttpServletResponse response,
                                      @RequestHeader(required = false) String
                                     range) throws FileNotFoundException {
        String fileName = request.getParameter("courseWareName");

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        String systemName = System.getProperty("os.name");
        String path;
        //如果操作系统是Windows
        if (systemName.startsWith("Windows")) {
            path = "D:\\bysj\\courseWare\\";
        }
        //如果是Linux
        else {
            path = "/bysj/courseWare/";
        }


        File file = new File(path  + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String returnFileName = "";
        try {
            returnFileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + returnFileName)
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }





}
