package com.cspi.notionboard.module.comment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    List<Map<String, Object>> selectAllComments(@Param("size") int size, @Param("offset") int offset, @Param("postId") Long postId);
    int countComments(Long postId);
    Map<String, Object> findByCommentId(Long commentId);
    void insertComment(Map<String, Object> comment);
    void updateComment(Map<String, Object> comment);
    void deleteComment(Long commentId);
}
