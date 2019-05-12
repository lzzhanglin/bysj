package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Chapter;

import java.util.List;

public interface ChapterService {

    Long createChapter(Chapter chapter);

    List<Chapter> getChapterList(Integer courseId);

    void deleteChapter(Long chapterId);

    void deleteChapterByCourseId(Integer courseId);

    void updateChapter(Chapter chapter);


}
