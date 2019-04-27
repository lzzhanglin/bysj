package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Chapter;

import java.util.List;

public interface ChapterService {

    Long createChapter(Chapter chapter);

    public List<Chapter> getChapterList(Integer courseId);
}
