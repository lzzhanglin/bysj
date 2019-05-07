package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.PracticalCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PracticalConditionMapper {

    void createPracticalCondition(PracticalCondition condition);

    List<PracticalCondition> getAllPracticalCondition();

    void deletePracticalCondition(@Param("conditionId") Long conditionId);
}
