package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Material;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialMapper {

    void createMaterial(Material material);

    void deleteMaterial(@Param("materialId") Long materialId);

    void deleteMaterialByAchievementId(@Param("achievementId") Long achievementId);

    List<Material> getMaterialListByAchievementId(@Param("achievementId") Long achievementId);
}
