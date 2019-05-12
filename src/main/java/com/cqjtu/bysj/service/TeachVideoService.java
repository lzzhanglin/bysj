package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.TeachVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachVideoService {
    void createTeachVideo(TeachVideo video);

    void deleteTeachVideo(Long videoId);

    List<TeachVideo> getVideoList(Long chapterId);
}
