package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.WebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@EnableScheduling
@Controller
public class WebSocketComponent {

    @Autowired
    private SimpMessagingTemplate simpMessageSendingOperations;//消息发送模板

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String notice = "default";

    private String noticeUsername = "default";

    private String noticeTime = "default";

//    @SendTo("/topic/ip")
//    @Scheduled(fixedRate = 1000 * 30)//每隔30秒向客户端发送一次数据
    @Scheduled(fixedRate = 1000 * 5 )
    public void pushMessage() {
        logger.info("推送完成");
        WebSocketMessage message = new WebSocketMessage();
        message.setSendTime(noticeTime);
        message.setUsername(noticeUsername);
        if (!Objects.equals(notice, "default")) {
            message.setExtra("notice");
            message.setContent(notice);
            //将消息推送给‘、topic/ip’的客户端
            simpMessageSendingOperations.convertAndSend("/topic/ip", message);
        }

    }

    @MessageMapping("/send")
    @SendTo("/topic/ip")
    public WebSocketMessage sendMessage(WebSocketMessage message) {
        logger.info("收到消息{}",message.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        String sendTime = sdf.format(new Date());
        message.setSendTime(sendTime);
        return message;
    }

    @MessageMapping("/notice")
    @SendTo("/topic/ip")
    public WebSocketMessage noticeMessage(WebSocketMessage message) {
        if (Objects.equals(message.getExtra(), "cancelTop")) {
            notice = "default";
            noticeUsername = "default";
            noticeTime = "default";
            message.setExtra("canceled");
            return message;
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
            String sendTime = sdf.format(new Date());
            message.setSendTime(sendTime);
            notice = message.getContent();
            noticeTime = sendTime;
            noticeUsername = message.getUsername();
            return message;
        }
    }
}
