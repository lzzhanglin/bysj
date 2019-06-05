package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketMessage {

    private String username;

    private String jobNo;

    private String sendTime;

    private String content;

    private String extra;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String username, String jobNo, String content, String extra) {
        this.username = username;
        this.jobNo = jobNo;
        this.content = content;
        this.extra = extra;
    }

    public WebSocketMessage(String username,String jobNo,String sendTime, String content,String extra) {
        this.username = username;
        this.jobNo = jobNo;
        this.sendTime = sendTime;
        this.content = content;
        this.extra = extra;
    }

}
