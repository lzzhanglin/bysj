package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.AccessRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccessRecordMapper {

    void createAccessRecord(AccessRecord accessRecord);

    List<AccessRecord> getRecentRecords();
}
