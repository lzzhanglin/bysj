package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseInfo {

    private Long courseInfoId;

    private String createTime;

    private String creatorJobNo;

    private String infoName;

    private String infoDetail;

    private Integer infoType;

    public CourseInfo(String creatorJobNo, String infoName, String infoDetail, Integer infoType) {
        this.creatorJobNo = creatorJobNo;
        this.infoName = infoName;
        this.infoDetail = infoDetail;
        this.infoType = infoType;
    }

    public CourseInfo(Long courseInfoId, String createTime, String creatorJobNo, String infoName, String infoDetail, Integer infoType) {
        this.courseInfoId = courseInfoId;
        this.createTime = createTime;
        this.creatorJobNo = creatorJobNo;
        this.infoName = infoName;
        this.infoDetail = infoDetail;
        this.infoType = infoType;
    }
}
