package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.AccessRecord;

import java.util.List;
import java.util.Map;

public interface AccessRecordService {

    void createAccessRecord(AccessRecord accessRecord);

    List<AccessRecord> getRecentRecords();
}
