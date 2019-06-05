package com.cqjtu.bysj.mapper;

import com.cqjtu.bysj.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttachmentMapper {
    void uploadFile(Attachment attachment);

    List<Attachment> getFileByType(@Param("fileType") String fileType);

    List<Attachment> getFileByDoubleIdAndType(@Param("courseId")Long courseId,
                                              @Param("chapterId")Long chapterId,
                                              @Param("fileType") String fileType);

    List<Attachment> getFileByCourseIdAndType(@Param("courseId")Long courseId,
                                              @Param("fileType") String fileType);

    void deleteFileById(@Param("attachId") Long attachId);

    void deleteFileByCourseId(@Param("courseId") Long courseId);

    void deleteFileByChapterId(@Param("chapterId") Long chapterId);

}

