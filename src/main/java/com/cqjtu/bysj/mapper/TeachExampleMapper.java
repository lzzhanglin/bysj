package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.TeachExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachExampleMapper {

    void createTeachExample(TeachExample example);

    List<TeachExample> getTeachExampleList(@Param("courseId") Long courseId);

    void deleteTeachExample(@Param("exampleId") Long exampleId);

}
