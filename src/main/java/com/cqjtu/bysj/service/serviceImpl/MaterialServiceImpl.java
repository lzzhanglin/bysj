package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Material;
import com.cqjtu.bysj.mapper.MaterialMapper;
import com.cqjtu.bysj.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public void createMaterial(Material material) {
        materialMapper.createMaterial(material);
    }

    @Override
    public List<Material> getMaterialListByAchievementId(Long achievementId) {
        List<Material> materials = materialMapper.getMaterialListByAchievementId(achievementId);
        String a;
        return materialMapper.getMaterialListByAchievementId(achievementId);
    }

    @Override
    public void deleteMaterial(Long materialId) {
        materialMapper.deleteMaterial(materialId);
    }

    @Override
    public void deleteMaterialByAchievementId(Long achievementId) {
        materialMapper.deleteMaterialByAchievementId(achievementId);
    }
}
