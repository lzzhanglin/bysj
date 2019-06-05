package com.cqjtu.bysj.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {

    private String attachId;

    private String attachName;

    private String uploadTime;

    private Long courseId;

    private Long chapterId;

    private String fileType;

    private String creatorJobNo;

    private String externalLink;

    private String previewLink;

    private String describe;
}
