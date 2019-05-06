package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Achievement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AchievementMapper {

    void createAchievement(Achievement achievement);

    void deleteAchievement(@Param("achievementId") Long achievementId);

    List<Achievement> getAllAchievementsByKind(@Param("achievementKind")String achievementKind);

    void updateAchievement(Achievement achievement);
}
