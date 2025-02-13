package com.cspi.notionboard.module.comment.dao;

import com.cspi.notionboard.module.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommnetMapper {
    List<CommentDto> selectAllComments();
    void insertComment(CommentDto commentDto);
    void updateComment(Long commentId, @Param("commentDto") CommentDto commentDto);
    void deleteComment(Long commentId, @Param("password") String password);
}
