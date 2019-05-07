package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.PracticalCondition;

import java.util.List;

public interface PracticalConditionService {

    void createPracticalCondition(PracticalCondition condition);

    List<PracticalCondition> getAllPracticalCondition();

    void deletePracticalCondition(Long conditionId);
}
