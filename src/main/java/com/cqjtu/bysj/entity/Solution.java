package com.cqjtu.bysj.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solution {

    private Long solutionId;

    private String solutionName;

    private Long exerciseId;

    private String externalLink;

    private String previewLink;

    private String creatorJobNo;

    private String uploadTime;

    private Long courseId;

    public Solution(Long solutionId, String solutionName, Long exerciseId,
                    String externalLink, String previewLink, String creatorJobNo,
                    String uploadTime, Long courseId) {
        this.solutionId = solutionId;
        this.solutionName = solutionName;
        this.exerciseId = exerciseId;
        this.externalLink = externalLink;
        this.previewLink = previewLink;
        this.creatorJobNo = creatorJobNo;
        this.uploadTime = uploadTime;
        this.courseId = courseId;
    }

    public Solution(String solutionName, Long exerciseId, String externalLink, String previewLink, String creatorJobNo, Long courseId) {
        this.solutionName = solutionName;
        this.exerciseId = exerciseId;
        this.externalLink = externalLink;
        this.previewLink = previewLink;
        this.creatorJobNo = creatorJobNo;
        this.courseId = courseId;
    }
}
