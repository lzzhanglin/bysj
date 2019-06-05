package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ChapterMapper {
    Long createChapter(Chapter chapter);

    List<Chapter> getChapterList(@Param("courseId") Integer courseId);

    void deleteChapter(@Param("chapterId") Long chapterId);

    void deleteChapterByCourseId(@Param("courseId") Long courseId);

    void updateChapter(Chapter chapter);
}
