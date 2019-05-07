package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.PracticalCondition;
import com.cqjtu.bysj.mapper.PracticalConditionMapper;
import com.cqjtu.bysj.service.PracticalConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("conditionService")
public class PracticalConditionServiceImpl implements PracticalConditionService {

    @Autowired
    private PracticalConditionMapper conditionMapper;

    @Override
    public void createPracticalCondition(PracticalCondition condition){
        conditionMapper.createPracticalCondition(condition);
    }

    public List<PracticalCondition> getAllPracticalCondition(){
        return conditionMapper.getAllPracticalCondition();
    }

    public void deletePracticalCondition(Long conditionId) {
        conditionMapper.deletePracticalCondition(conditionId);
    }
}
