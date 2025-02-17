package com.cspi.notionboard.module.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    List<Map<String, Object>> selectAllComments(Long postId);
    Map<String, Object> findByCommentId(Long commentId);
    void insertComment(Map<String, Object> comment);
    void updateComment(Map<String, Object> comment);
    void deleteComment(Long commentId);
}
