package com.cqjtu.bysj.entity;


import lombok.Getter;
import lombok.Setter;

//该类为实践条件的实体类 存储图片信息
@Getter
@Setter
public class PracticalCondition {

    private Long practicalConditionId;

    private String practicalConditionName;

    private String uploadTime;

    private String creatorJobNo;

    private String describe;

    private Integer fileType;

    private String externalLink;

    private String previewLink;

    public PracticalCondition(String practicalConditionName, String creatorJobNo,
                              String describe, Integer fileType,String externalLink, String previewLink) {
        this.practicalConditionName = practicalConditionName;
        this.creatorJobNo = creatorJobNo;
        this.describe = describe;
        this.fileType = fileType;
        this.externalLink = externalLink;
        this.previewLink = previewLink;
    }
}
