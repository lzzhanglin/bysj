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
import java.util.Random;

@RestController
@RequestMapping("/courseWare")
public class CourseWareController {

    @Autowired
    private CourseWareService courseWareService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Resp createCourseWare(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String systemName = System.getProperty("os.name");
        String path;
        //如果操作系统是Windows
        if (systemName.startsWith("Windows")) {
            path = "D:\\upload\\";
        }
        //如果是Linux
        else {
            path = "/upload/";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            GetRandomFileName randomFileName = new GetRandomFileName();
            String courseWareName = randomFileName.getRandomFileName(fileName, 10);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path + courseWareName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                String creatorJobNo = request.getParameter("creatorJobNo");
                Long chapterId = Long.valueOf(request.getParameter("chapterId"));
                Integer courseId = Integer.valueOf(request.getParameter("courseId"));
                CourseWare courseWare = new CourseWare();
                courseWare.setCourseWareName(courseWareName);
                courseWare.setCreatorJobNo(creatorJobNo);
                courseWare.setCourseId(courseId);
                courseWare.setChapterId(chapterId);
                courseWareService.createCourseWare(courseWare);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Resp(RespCode.SUCCESS);
        }else {
            return new Resp(RespCode.FAILED);
        }
    }

    @RequestMapping(value = "getCourseWareList",method = RequestMethod.GET)
    public Resp getCourseWareList(HttpServletRequest request) {
        Long chapterId = Long.valueOf(request.getParameter("chapterId"));
        List<CourseWare> courseWareList = courseWareService.getCourseWareList(chapterId);
        return new Resp(RespCode.SUCCESS, courseWareList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteCourseWare(HttpServletRequest request) {
        Long courseWareId = Long.valueOf(request.getParameter("courseWareId"));
        String courseWareName = request.getParameter("courseWareName");
        courseWareService.deleteCourseWare(courseWareId);
        return new Resp(RespCode.SUCCESS);
    }


//    @RequestMapping(value = "/download",method = RequestMethod.GET)
//    public void getDownload(HttpServletRequest request, HttpServletResponse response,
//                                                           @RequestHeader(required = false) String
//                                                                   range) throws FileNotFoundException {
//        String fileName = request.getParameter("courseWareName");
//
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
//        String systemName = System.getProperty("os.name");
//        String path;
//        //如果操作系统是Windows
//        if (systemName.startsWith("Windows")) {
//            path = "D:\\upload\\";
//        }
//        //如果是Linux
//        else {
//            path = "/upload/";
//        }
//
//
//        File file = new File(path  + fileName);
//        String contentType = request.getServletContext().getMimeType(fileName);
//        response.setContentType(mediaType.getType());
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
////        response.setHeader("Accept-Ranges", "bytes");
////        response.setHeader("Content-Type", contentType);
//
//        response.setContentLength((int) file.length());
//        response.setStatus(response.SC_PARTIAL_CONTENT);
//
//
//        try {
//            BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
//
//            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
////            response.setHeader("Content-Disposition", "inline;filename="+new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
//
//            byte[] buffer = new byte[1024];
//            int bytesRead = 0;
//            while ((bytesRead = inStream.read(buffer)) != -1) {
//                outStream.write(buffer, 0, bytesRead);
//            }
//            outStream.flush();
//            inStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

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
            path = "D:\\upload\\";
        }
        //如果是Linux
        else {
            path = "/upload/";
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
