package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeachVideo {
    private Long videoId;

    private String videoName;

    private Long courseId;

    private Long chapterId;

    private String uploadTime;

    private String creatorJobNo;

    private String externalLink;

    private String previewLink;


}
