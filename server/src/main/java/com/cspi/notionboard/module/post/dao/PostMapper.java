package com.cspi.notionboard.module.post.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
    void insertPost(Map<String, Object> post);
    List<Map<String, Object>> selectAllPosts();
    List<Map<String, Object>> searchedPosts(String keyword);
    Map<String, Object> selectPost(Long postId);
    Map<String, Object> findById(Long postId);
    void updatePost(Map<String, Object> post);
    void deletePost(Long postId);
}
