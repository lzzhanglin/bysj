package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {

    private Long teacherId;

    private String teacherName;

    private String sex;

    private String birthDay;

    private String education;//学历

    private String jobTitle;//职称

    private String position;

    private String affiliatedCollege;//所属学院

    private String introduction;

    public Teacher(Long teacherId, String teacherName, String sex, String birthDay, String education, String jobTitle, String position,String affiliatedCollege, String introduction) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.education = education;
        this.jobTitle = jobTitle;
        this.position = position;
        this.affiliatedCollege = affiliatedCollege;
        this.introduction = introduction;
    }
}
