package com.cqjtu.bysj.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
public class MessageController {


    @MessageMapping("/greeting")
    public String handle(String greeting) {
        return "[user]:" + greeting;
    }

}
