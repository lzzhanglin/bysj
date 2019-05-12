package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.TeachExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachExampleService {
    void createTeachExample(TeachExample example);

    List<TeachExample> getTeachExmapleList(Long courseId);

    void deleteTeachExample(Long exampleId);
}
