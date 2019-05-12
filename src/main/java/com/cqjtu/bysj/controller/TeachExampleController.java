package com.cqjtu.bysj.controller;


import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.entity.TeachExample;
import com.cqjtu.bysj.service.TeachExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/teachExample")
public class TeachExampleController {

    @Autowired
    private TeachExampleService exampleService;

    @RequestMapping(value = "writeFileInfo", method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {
        String teachExampleName = request.getParameter("teachExampleName");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");
        TeachExample example = new TeachExample();
        example.setTeachExampleName(teachExampleName);
        example.setCourseId(courseId);
        example.setCreatorJobNo(creatorJobNo);
        example.setExternalLink(externalLink);
        example.setPreviewLink(previewLink);
        exampleService.createTeachExample(example);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getTeachExampleList", method = RequestMethod.GET)
    public Resp getTeachExampleList(HttpServletRequest request) {
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        List<TeachExample> exampleList = exampleService.getTeachExmapleList(courseId);
        return new Resp(RespCode.SUCCESS, exampleList);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteExample(HttpServletRequest request) {
        Long exampleId = Long.valueOf(request.getParameter("exampleId"));
        exampleService.deleteTeachExample(exampleId);
        return new Resp(RespCode.SUCCESS);
    }
}
