package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Solution;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SolutionMapper {
    void createSolution(Solution solution);

    void deleteSolution(@Param("solutionId") Long solutionId);

    List<Solution> getSolutionList(@Param("exerciseId") Long exerciseId);
}
