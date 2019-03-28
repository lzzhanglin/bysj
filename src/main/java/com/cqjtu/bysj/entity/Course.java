package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Course {
    private Integer courseId;

    private String courseName;

    private String courseKind;

    private String courseIntro;

    private String createTime;

    private String timeBegin;

    private String timeEnd;

    private Long teacherId;

    private String teacherName;

    private Integer electorNum;


}
