package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Achievement {

    private Long achievementId;

    private String achievementName;

    private String achievementKind;

    private String detail;

    private String createTime;

    public Achievement(Long achievementId, String achievementName, String achievementKind, String detail) {
        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementKind = achievementKind;
        this.detail = detail;
    }
}
