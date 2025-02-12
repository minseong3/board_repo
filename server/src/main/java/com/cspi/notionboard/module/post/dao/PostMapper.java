package com.cspi.notionboard.module.post.dao;

import com.cspi.notionboard.module.post.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(PostDto postDto);
    List<PostDto> selectAllPosts();
    List<PostDto> searchedPosts(@Param("keyword") String keyword);
    PostDto selectPost(Long postId);
    PostDto findById(@Param("postId") Long postId);
    void updatePost(Long postId, @Param("postDto") PostDto postDto);
    void deletePost(Long postId, @Param("password") String password);

}
