package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Achievement;
import com.cqjtu.bysj.mapper.AchievementMapper;
import com.cqjtu.bysj.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("achievementService")
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public void createAchievement(Achievement achievement) {
        achievementMapper.createAchievement(achievement);
    }

    @Override
    public void updateAchievement(Achievement achievement) {
        achievementMapper.updateAchievement(achievement);
    }

    @Override
    public List<Achievement> getAllAchievementsByKind(String achievementKind) {
        return achievementMapper.getAllAchievementsByKind(achievementKind);
    }

    @Override
    public void deleteAchievement(Long achievementId) {
        achievementMapper.deleteAchievement(achievementId);
    }
}
