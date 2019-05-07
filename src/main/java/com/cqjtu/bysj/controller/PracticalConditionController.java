package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Material;
import com.cqjtu.bysj.entity.PracticalCondition;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
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
import java.util.List;

@RequestMapping("/practicalCondition")
@RestController
public class PracticalConditionController {

    @Autowired
    private PracticalConditionService conditionService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Resp createCourseWare(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String systemName = System.getProperty("os.name");
        String path;
        //如果操作系统是Windows
        if (systemName.startsWith("Windows")) {
            path = "D:\\bysj\\experiment\\";
        }
        //如果是Linux
        else {
            path = "/bysj/experiment/";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            GetRandomFileName randomFileName = new GetRandomFileName();
            String conditionName = randomFileName.getRandomFileName(fileName, 10);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path + conditionName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                String creatorJobNo = request.getParameter("creatorJobNo");
                String describe = request.getParameter("describe");
                PracticalCondition condition = new PracticalCondition(conditionName,creatorJobNo,describe);
                conditionService.createPracticalCondition(condition);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Resp(RespCode.SUCCESS);
        }else {
            return new Resp(RespCode.FAILED);
        }
    }


    @RequestMapping(value = "getAllImg", method = RequestMethod.GET)
    public Resp getAllImg(HttpServletRequest request) {
        List<PracticalCondition> conditionList = conditionService.getAllPracticalCondition();
        return new Resp(RespCode.SUCCESS, conditionList);
    }

    @RequestMapping(value = "/show",produces = MediaType.IMAGE_JPEG_VALUE,method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getDownload(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        List<PracticalCondition> conditionList = conditionService.getAllPracticalCondition();
        String fileName = conditionList.get(0).getPracticalConditionName();
        String imgName = request.getParameter("imgName");
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, imgName);
        String systemName = System.getProperty("os.name");
        String path;
        //如果操作系统是Windows
        if (systemName.startsWith("Windows")) {
            path = "D:\\bysj\\experiment\\";
        }
        //如果是Linux
        else {
            path = "/bysj/experiment/";
        }
        File file = new File(path  + imgName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String returnFileName = "";
        try {
            returnFileName = new String(imgName.getBytes("GB2312"), "ISO-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + returnFileName)
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteImg(HttpServletRequest request) {
        String string = request.getParameter("conditionId");
        Long conditionId = Long.valueOf(request.getParameter("conditionId"));
        conditionService.deletePracticalCondition(conditionId);
        return new Resp(RespCode.SUCCESS);
    }
}
