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

    @RequestMapping(value = "writeFileInfo",method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {
        String materialName = request.getParameter("materialName");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Long achievementId = Long.valueOf(request.getParameter("achievementId"));
        String externalLink = request.getParameter("externalLink");

        Material material = new Material(materialName, achievementId, creatorJobNo, externalLink);
        materialService.createMaterial(material);
        return new Resp(RespCode.SUCCESS);

    }

    @RequestMapping(value = "getMaterialList", method = RequestMethod.GET)
    public Resp getMaterialList(HttpServletRequest request) {
        Long achievementId = Long.valueOf(request.getParameter("achievementId"));
        List<Material> materialList = materialService.getMaterialListByAchievementId(achievementId);
        return new Resp(RespCode.SUCCESS, materialList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteMaterial(HttpServletRequest request) {
        Long materialId = Long.valueOf(request.getParameter("materialId"));
        materialService.deleteMaterial(materialId);
        return new Resp(RespCode.SUCCESS);
    }



}
