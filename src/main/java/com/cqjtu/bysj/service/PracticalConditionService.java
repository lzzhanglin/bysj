package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.PracticalCondition;

import java.util.List;

public interface PracticalConditionService {

    void createPracticalCondition(PracticalCondition condition);

    List<PracticalCondition> getAllFileByType(Integer fileType);

    void deletePracticalCondition(Long conditionId);
}
