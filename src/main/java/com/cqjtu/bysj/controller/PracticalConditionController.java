package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Material;
import com.cqjtu.bysj.entity.PracticalCondition;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.qiniu.QiniuUpload;
import com.cqjtu.bysj.service.PracticalConditionService;
import com.cqjtu.bysj.util.GetRandomFileName;
import com.cqjtu.bysj.util.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/practicalCondition")
@RestController
public class PracticalConditionController {

    @Autowired
    private PracticalConditionService conditionService;

    @Autowired
    private ServletContext servletContext;

    //获取上传图片的token以及随机文件名
    @RequestMapping(value = "getToken")
    public Resp getToken(HttpServletRequest request) {
        String fileName = request.getParameter("fileName");
        String bucketName = request.getParameter("bucketName");
        GetRandomFileName getRandomFileName = new GetRandomFileName();
        String newFileName = getRandomFileName.getRandomFileName(fileName,10);
        QiniuUpload upload = new QiniuUpload();
        //根据不同文件类型使用不同的bucket
        String token = upload.getToken(bucketName);
        List<String> infoList = new ArrayList<>();
        infoList.add(token);
        infoList.add(newFileName);
        return new Resp(RespCode.SUCCESS, infoList);
    }

    @RequestMapping(value = "writeFileInfo",method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {
        String fileName = request.getParameter("fileName");
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");
        String creatorJobNo = request.getParameter("creatorJobNo");
        String describe = request.getParameter("describe");
        Integer fileType = Integer.valueOf(request.getParameter("fileType"));
        PracticalCondition condition = new PracticalCondition(fileName, creatorJobNo, describe, fileType, externalLink,previewLink);
        conditionService.createPracticalCondition(condition);
        return new Resp(RespCode.SUCCESS);
    }


    @RequestMapping(value = "getAllFileByType", method = RequestMethod.GET)
    public Resp getAllFileByType(HttpServletRequest request) {
        //filetype字段 1代表实践条件的图片 2代表实验项目指导书 3代表结课大作业

        Integer fileType = Integer.valueOf(request.getParameter("fileType"));
        List<PracticalCondition> conditionList = conditionService.getAllFileByType(fileType);

        return new Resp(RespCode.SUCCESS, conditionList);
    }
//
//    @RequestMapping(value = "/showImg",produces = MediaType.IMAGE_JPEG_VALUE,method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> showImg(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
//        String imgName = request.getParameter("imgName");
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, imgName);
//        String systemName = System.getProperty("os.name");
//        String path;
//        //如果操作系统是Windows
//        if (systemName.startsWith("Windows")) {
//            path = "D:\\bysj\\experiment\\";
//        }
//        //如果是Linux
//        else {
//            path = "/bysj/experiment/";
//        }
//        File file = new File(path  + imgName);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        String returnFileName = "";
//        try {
//            returnFileName = new String(imgName.getBytes("GB2312"), "ISO-8859-1");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok()
//                // Content-Disposition
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + returnFileName)
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
//                .contentLength(file.length()) //
//                .body(resource);
//    }

//    @RequestMapping(value = "/showWord",produces = MediaType.IMAGE_JPEG_VALUE,method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> showWord(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
//        String wordName = request.getParameter("wordName");
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, wordName);
//        String systemName = System.getProperty("os.name");
//        String path;
//        //如果操作系统是Windows
//        if (systemName.startsWith("Windows")) {
//            path = "D:\\bysj\\experiment\\";
//        }
//        //如果是Linux
//        else {
//            path = "/bysj/experiment/";
//        }
//        File file = new File(path  + wordName);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        String returnFileName = "";
//        try {
//            returnFileName = new String(wordName.getBytes("GB2312"), "ISO-8859-1");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok()
//                // Content-Disposition
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + returnFileName)
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
//                .contentLength(file.length()) //
//                .body(resource);
//    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteImg(HttpServletRequest request) {
        Long conditionId = Long.valueOf(request.getParameter("conditionId"));
        conditionService.deletePracticalCondition(conditionId);
        return new Resp(RespCode.SUCCESS);
    }
}
