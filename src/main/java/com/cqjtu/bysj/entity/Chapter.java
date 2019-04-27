package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chapter {

    private Long chapterId;

    private String createTime;

    private String creatorJobNo;

    private Integer chapterOrder;

    private String chapterName;

    private String chapterContent;


    private Integer courseId;
}
