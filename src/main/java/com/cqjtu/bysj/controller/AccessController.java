package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.AccessRecord;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.service.AccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accessRecord")
public class AccessController {

    @Autowired
    private AccessRecordService accessRecordService;

    @RequestMapping(value = "getRecentRecords", method = RequestMethod.GET)
    public Resp getRecentRecords() {
        List<AccessRecord> list = accessRecordService.getRecentRecords();
        return new Resp(RespCode.SUCCESS, list);

    }
}
