package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.Achievement;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.AchievementService;
import com.cqjtu.bysj.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/achievement")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private MaterialService materialService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Resp createAchievement(HttpServletRequest request) {
        String achievementName = request.getParameter("achievementName");
        String achievementKind = request.getParameter("achievementKind");
        String detail = request.getParameter("detail");
        Achievement achievement= new Achievement(null,achievementName,achievementKind,detail);
        achievementService.createAchievement(achievement);
        return new Resp(RespCode.SUCCESS,achievement.getAchievementId());
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateAchievement(HttpServletRequest request) {
        Long achievementId = Long.valueOf(request.getParameter("achievementId"));
        String achievementName = request.getParameter("achievementName");
        String achievementKind = request.getParameter("achievementKind");
        String detail = request.getParameter("detail");
        Achievement achievement= new Achievement(achievementId,achievementName,achievementKind,detail);
        achievementService.updateAchievement(achievement);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "getAllAchievements", method = RequestMethod.GET)
    public Resp getAllAchievements(HttpServletRequest request) {
        String kind = request.getParameter("kind");
        List<Achievement> achievements = achievementService.getAllAchievementsByKind(kind);
        return new Resp(RespCode.SUCCESS, achievements);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteAchievement(HttpServletRequest request) {
        Long achievementId = Long.valueOf(request.getParameter("achievementId"));
        achievementService.deleteAchievement(achievementId);
        materialService.deleteMaterialByAchievementId(achievementId);
        return new Resp(RespCode.SUCCESS);
    }
}
