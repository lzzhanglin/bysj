package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessRecord {

    private Long accessId;

    private String accessTime;

    private String IP;

    private String username;

    private String jobNo;
}
