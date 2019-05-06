package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Achievement;

import java.util.List;

public interface AchievementService {

    void createAchievement(Achievement achievement);

    void updateAchievement(Achievement achievement);

    List<Achievement> getAllAchievementsByKind(String achievementKind);

    void deleteAchievement(Long achievementId);
}
