package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Solution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SolutionService {
    void createSolution(Solution solution);

    void deleteSolution(@Param("solutionId") Long solutionId);

    List<Solution> getSolutionList(@Param("courseId") Long courseId);
}
