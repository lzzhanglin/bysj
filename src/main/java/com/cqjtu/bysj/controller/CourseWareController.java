package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.CourseWare;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.CourseWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

@RestController
@RequestMapping("/courseWare")
public class CourseWareController {

    @Autowired
    private CourseWareService courseWareService;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Resp createCourseWare(@RequestParam("file") MultipartFile file, HttpServletRequest request) {


        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String courseWareName = getRandomFileName(fileName,10);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("D:\\IdeaProjects\\bysj\\bysj\\src\\main\\resources\\upload\\"+courseWareName)));
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

    //根据随机字符串生成文件名
    public static String getRandomFileName(String fileName, int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        String randomStr = sb.toString();
        StringBuilder sb1 = new StringBuilder();
        int index = fileName.lastIndexOf(".");

        String prefix = fileName.substring(0, index);
        String suffix = fileName.substring(index+1, fileName.length());
        sb1.append(prefix);
        sb1.append("-");
        sb1.append(randomStr);
        sb1.append(".");
        sb1.append(suffix);

        return sb1.toString();
    }
}
