package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Material;

import java.util.List;

public interface MaterialService {

    void createMaterial(Material material);

    List<Material> getMaterialListByAchievementId(Long achievementId);

    void deleteMaterial(Long materialId);

    void deleteMaterialByAchievementId(Long achievementId);
}
