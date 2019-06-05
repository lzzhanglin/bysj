package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.Attachment;
import com.cqjtu.bysj.mapper.AttachmentMapper;
import com.cqjtu.bysj.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public void uploadFile(Attachment attachment){
        attachmentMapper.uploadFile(attachment);
    }

    public List<Attachment> getFileByType(String fileType){
        return attachmentMapper.getFileByType(fileType);
    }

    public List<Attachment> getFileByDoubleIdAndType(Long courseId, Long chapterId, String fileType){
        return attachmentMapper.getFileByDoubleIdAndType(courseId, chapterId, fileType);
    }

    public List<Attachment> getFileByCourseIdAndType(Long courseId, String fileType){
        return attachmentMapper.getFileByCourseIdAndType(courseId, fileType);
    }

    public void deleteFileById(Long attachId){
        attachmentMapper.deleteFileById(attachId);
    }

    public void deleteFileByCourseId(Long courseId){
        attachmentMapper.deleteFileByCourseId(courseId);
    }

    public void deleteFileByChapterId(Long chapterId){
        attachmentMapper.deleteFileByChapterId(chapterId);
    }
}
