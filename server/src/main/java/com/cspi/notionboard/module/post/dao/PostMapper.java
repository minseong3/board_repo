package com.cspi.notionboard.module.post.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
    void insertPost(Map<String, Object> post);
    List<Map<String, Object>> selectAllPosts(@Param("size") int limit, @Param("offset") int offset);
    int countPosts();
    int countSearchedPosts();
    List<Map<String, Object>> searchedPosts(@Param("size") int limit, @Param("offset") int offset, @Param("keyword") String keyword);
    Map<String, Object> selectPost(Long postId);
    Map<String, Object> findByPostId(Long postId);
    void updatePost(Map<String, Object> post);
    void deletePost(Long postId);
}
