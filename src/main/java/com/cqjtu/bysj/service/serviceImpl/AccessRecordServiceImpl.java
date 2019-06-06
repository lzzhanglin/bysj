package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.AccessRecord;
import com.cqjtu.bysj.mapper.AccessRecordMapper;
import com.cqjtu.bysj.service.AccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("accessRecordService")
public class AccessRecordServiceImpl implements AccessRecordService {

    @Autowired
    private AccessRecordMapper recordMapper;

    @Override
    public void createAccessRecord(AccessRecord accessRecord){
        recordMapper.createAccessRecord(accessRecord);
    }

    @Override
    public List<AccessRecord> getRecentRecords() {
        List<AccessRecord> recordList = recordMapper.getRecentRecords();
        List<Map<String, Object>> resultList = new ArrayList<>();
        return recordList;
    }
}
