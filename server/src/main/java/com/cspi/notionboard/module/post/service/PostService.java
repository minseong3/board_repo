package com.cspi.notionboard.module.post.service;

import com.cspi.notionboard.global.authentication.PasswordUtil;
import com.cspi.notionboard.global.exception.NotFoundException;
import com.cspi.notionboard.module.comment.dao.CommentMapper;
import com.cspi.notionboard.module.post.dao.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;

    public void insertPost(Map<String, Object> post) {
        // 비밀번호 해싱 후 저장
        String hashedPassword = PasswordUtil.hashPassword((String) post.get("postPassword"));
        post.put("password", hashedPassword);
        postMapper.insertPost(post);
    }

    public List<Map<String, Object>> getAllPosts() {
        return postMapper.selectAllPosts();
    }

    public Map<String, Object> selectPost(Long postId) {
        Map<String, Object> post = postMapper.selectPost(postId);
        List<Map<String, Object>> comments = commentMapper.selectAllComments(postId);
        post.put("comments", comments);
        return post;
    }

    public void updatePost(Long postId, Map<String, Object> post) {
        // 게시글 존재 유무 확인
        Map<String, Object> existingPost = postMapper.findById(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 비밀번호 검증
        if(!PasswordUtil.checkPassword((String) post.get("post_password"), (String) existingPost.get("post_password"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.updatePost(post);
    }

    public List<Map<String, Object>> searchedPosts(String keyword) {
        return postMapper.searchedPosts(keyword);
    }

    public void deletePost(Long postId, String password) {
        Map<String, Object> existingPost = postMapper.selectPost(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 비밀번호 검증 (평문의 password를 받아 해싱과정을 거치고 비교)
        if(!PasswordUtil.checkPassword(password, (String) existingPost.get("postPassword"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.deletePost(postId);
    }
}
