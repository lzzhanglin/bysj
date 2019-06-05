package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Chapter;
import com.cqjtu.bysj.mapper.ChapterMapper;
import com.cqjtu.bysj.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public Long createChapter(Chapter chapter) {
        chapterMapper.createChapter(chapter);
        return chapter.getChapterId();
    }

    @Override
    public List<Chapter> getChapterList(Integer courseId) {
        return chapterMapper.getChapterList(courseId);
    }

    @Override
    public void deleteChapter(Long chapterID) {
        chapterMapper.deleteChapter(chapterID);
    }

    @Override
    public void deleteChapterByCourseId(Long courseId) {
        chapterMapper.deleteChapterByCourseId(courseId);
    }

    @Override
    public void updateChapter(Chapter chapter){
        chapterMapper.updateChapter(chapter);
    }


}
