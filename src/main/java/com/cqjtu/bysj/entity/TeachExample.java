package com.cqjtu.bysj.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeachExample {

    private Long teachExampleId;

    private String teachExampleName;

    private String uploadTime;

    private String creatorJobNo;

    private Long courseId;

    private String externalLink;

    private String previewLink;

    public TeachExample() {
    }
}
