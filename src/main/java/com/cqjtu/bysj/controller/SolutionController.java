package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.entity.Solution;
import com.cqjtu.bysj.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @RequestMapping(value = "writeFileInfo", method = RequestMethod.POST)
    public Resp writeFileInfo(HttpServletRequest request) {
        String solutionName = request.getParameter("solutionName");
        String creatorJobNo = request.getParameter("creatorJobNo");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Long exerciseId = Long.valueOf(request.getParameter("exerciseId"));
        String externalLink = request.getParameter("externalLink");
        String previewLink = request.getParameter("previewLink");
        Solution solution = new Solution(solutionName,exerciseId,externalLink,previewLink,creatorJobNo,courseId);
        solutionService.createSolution(solution);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getSolutionList", method = RequestMethod.GET)
    public Resp getSolutionList(HttpServletRequest request) {
//        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Long exerciseId = Long.valueOf(request.getParameter("exerciseId"));
        List<Solution> solutions = solutionService.getSolutionList(exerciseId);
        return new Resp(RespCode.SUCCESS, solutions);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteSolution(HttpServletRequest request) {
        Long solutionId = Long.valueOf(request.getParameter("solutionId"));
        solutionService.deleteSolution(solutionId);
        return new Resp(RespCode.SUCCESS);
    }
}
