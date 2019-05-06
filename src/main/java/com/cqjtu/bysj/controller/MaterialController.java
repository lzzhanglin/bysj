package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.Material;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.MaterialService;
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

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

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
            String materialName = randomFileName.getRandomFileName(fileName, 10);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path + materialName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                String creatorJobNo = request.getParameter("creatorJobNo");
                Long achievementId = Long.valueOf(request.getParameter("achievementId"));
                Material material = new Material(materialName,achievementId,creatorJobNo);
                materialService.createMaterial(material);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Resp(RespCode.SUCCESS);
        }else {
            return new Resp(RespCode.FAILED);
        }
    }

    @RequestMapping(value = "getMaterialList", method = RequestMethod.GET)
    public Resp getMaterialList(HttpServletRequest request) {
        Long achievementId = Long.valueOf(request.getParameter("achievementId"));
        List<Material> materialList = materialService.getMaterialListByAchievementId(achievementId);
        return new Resp(RespCode.SUCCESS, materialList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteMaterial(HttpServletRequest request) {
        String string = request.getParameter("materialId");
        Long materialId = Long.valueOf(request.getParameter("materialId"));
        materialService.deleteMaterial(materialId);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getDownload(HttpServletRequest request, HttpServletResponse response,
                                                           @RequestHeader(required = false) String
                                                                   range) throws FileNotFoundException {
        String fileName = request.getParameter("materialName");

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
