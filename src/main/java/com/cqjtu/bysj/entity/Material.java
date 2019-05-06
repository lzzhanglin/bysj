package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material {

    private Long materialId;

    private String materialName;

    private String uploadTime;

    private Long achievementId;

    private String creatorJobNo;

    public Material(String materialName, Long achievementId, String creatorJobNo) {
        this.materialName = materialName;
        this.achievementId = achievementId;
        this.creatorJobNo = creatorJobNo;
    }

    public Material(Long materialId, String materialName, String uploadTime, Long achievementId, String creatorJobNo) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.uploadTime = uploadTime;
        this.achievementId = achievementId;
        this.creatorJobNo = creatorJobNo;
    }
}
