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

    private String externalLink;

    public Material(String materialName, Long achievementId, String creatorJobNo, String externalLink) {
        this.materialName = materialName;
        this.achievementId = achievementId;
        this.creatorJobNo = creatorJobNo;
        this.externalLink = externalLink;
    }

    public Material(Long materialId, String materialName, String uploadTime,
                    Long achievementId, String creatorJobNo, String externalLink) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.uploadTime = uploadTime;
        this.achievementId = achievementId;
        this.creatorJobNo = creatorJobNo;
        this.externalLink = externalLink;
    }
}
