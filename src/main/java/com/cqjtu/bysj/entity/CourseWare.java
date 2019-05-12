package com.cqjtu.bysj.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseWare {

    private Long courseWareId;

    private String createTime;

    private String creatorJobNo;

    private String courseWareName;

    private Integer courseId;

    private Long chapterId;

    private String externalLink;

    private String previewLink;

    //1 课件 ppt 2 word 课程习题 3 参考答案 word
    private Integer wareType;
}
