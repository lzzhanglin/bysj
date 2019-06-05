package com.cqjtu.bysj.service;

import com.cqjtu.bysj.entity.Attachment;

import java.util.List;

public interface AttachmentService {
    void uploadFile(Attachment attachment);

    List<Attachment> getFileByType(String fileType);

    List<Attachment> getFileByDoubleIdAndType(Long courseId, Long chapterId, String fileType);

    List<Attachment> getFileByCourseIdAndType(Long courseId, String fileType);

    void deleteFileById(Long attachId);

    void deleteFileByCourseId(Long courseId);

    void deleteFileByChapterId(Long courseId);
}
