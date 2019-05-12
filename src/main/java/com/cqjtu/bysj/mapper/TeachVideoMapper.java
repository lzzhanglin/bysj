package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.TeachVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachVideoMapper {

    void createTeachVideo(TeachVideo video);

    void deleteTeachVideo(@Param("videoId") Long videoId);

    List<TeachVideo> getVideoList(@Param("chapterId") Long chapterId);
}
