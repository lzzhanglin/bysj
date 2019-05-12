package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.TeachVideo;
import com.cqjtu.bysj.mapper.TeachVideoMapper;
import com.cqjtu.bysj.service.TeachVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teachVideoService")
public class TeachVideoServiceImpl implements TeachVideoService {

    @Autowired
    private TeachVideoMapper videoMapper;

    public void createTeachVideo(TeachVideo video){
        videoMapper.createTeachVideo(video);
    }

    public void deleteTeachVideo(Long videoId){
        videoMapper.deleteTeachVideo(videoId);
    }

    public List<TeachVideo> getVideoList(Long chapterId){
        return videoMapper.getVideoList(chapterId);
    }
}
