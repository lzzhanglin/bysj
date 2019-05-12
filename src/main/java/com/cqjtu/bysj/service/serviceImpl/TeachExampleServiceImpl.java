package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.TeachExample;
import com.cqjtu.bysj.mapper.TeachExampleMapper;
import com.cqjtu.bysj.service.TeachExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teachExampleService")
public class TeachExampleServiceImpl implements TeachExampleService {



    @Autowired
    private TeachExampleMapper teachExampleMapper;

    @Override
    public void createTeachExample(TeachExample example){
        teachExampleMapper.createTeachExample(example);
    }

    @Override
    public List<TeachExample> getTeachExmapleList(Long courseId){
        return teachExampleMapper.getTeachExampleList(courseId);
    }

    @Override
    public void deleteTeachExample(Long exampleId){
        teachExampleMapper.deleteTeachExample(exampleId);
    }
}
