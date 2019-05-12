package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Solution;
import com.cqjtu.bysj.mapper.SolutionMapper;
import com.cqjtu.bysj.service.SolutionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("solutionService")
public class SolutionServiceImpl implements SolutionService {


    @Autowired
    private SolutionMapper solutionMapper;

    @Override
    public void createSolution(Solution solution){
        solutionMapper.createSolution(solution);
    }

    @Override
    public void deleteSolution(Long solutionId){
        solutionMapper.deleteSolution(solutionId);
    }

    @Override
    public List<Solution> getSolutionList(Long exerciseId){
        return solutionMapper.getSolutionList(exerciseId);
    }
}
