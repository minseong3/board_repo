package com.cspi.notionboard.module.post.service;

import com.cspi.notionboard.global.authentication.PasswordUtil;
import com.cspi.notionboard.global.exception.NotFoundException;
import com.cspi.notionboard.module.comment.dao.CommentMapper;
import com.cspi.notionboard.module.post.dao.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        String hashedPassword = PasswordUtil.hashPassword((String) post.get("post_password"));
        log.info("hashedPassword = {}", hashedPassword);
        post.put("postPassword", hashedPassword);
        postMapper.insertPost(post);
    }

    public Page<Map<String, Object>> getAllPosts(Pageable pageable) {
        // 전체 게시글 개수 조회
        int totalCount = postMapper.countPosts();

        List<Map<String, Object>> posts = postMapper.selectAllPosts(pageable.getPageSize(), (int) pageable.getOffset());

        return new PageImpl<>(posts, pageable, totalCount);
    }

    public Map<String, Object> selectPost(Long postId, Pageable pageable) {
        // 해당 게시글 가져오기
        Map<String, Object> post = postMapper.selectPost(postId);

        // 게시글에 대한 댓글 개수
        int totalComments = commentMapper.countComments(postId);

        // 댓글 목록 조회
        List<Map<String, Object>> comments = commentMapper.selectAllComments(pageable.getPageSize(), (int) pageable.getOffset(), postId);

        // 댓글들을 Page 객체로 변환
        Page<Map<String, Object>> commentPage = new PageImpl<>(comments, pageable, totalComments);

        log.info("post : {}", post);
        post.put("comments", commentPage);

        return post;
    }

    public void updatePost(Long postId, Map<String, Object> post) {
        // 게시글 존재 유무 확인
        Map<String, Object> targetPost = postMapper.findByPostId(postId);
        if(targetPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }
        log.info("post : {}", post);
        log.info("targetPost : {}", targetPost);

        // 비밀번호 검증
        if(!PasswordUtil.checkPassword((String) post.get("postPassword"), (String) targetPost.get("post_password"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.updatePost(post);
    }

    public Page<Map<String, Object>> searchedPosts(Pageable pageable, String keyword) {
        int totalSearchedCount = postMapper.countSearchedPosts();

        List<Map<String, Object>> searchedPosts = postMapper.searchedPosts(pageable.getPageSize(), (int) pageable.getOffset() , keyword);

        return new PageImpl<>(searchedPosts, pageable, totalSearchedCount);
    }

    public void deletePost(Long postId, String password) {
        // 게시글 존재 유무 확인
        Map<String, Object> existingPost = postMapper.findByPostId(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        log.info("password : {}", password);
        log.info("(String) existingPost.get(\"post_password\"): {}", (String)existingPost.get("post_password"));
        // 비밀번호 검증
        if(!PasswordUtil.checkPassword(password, (String) existingPost.get("post_password"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.deletePost(postId);
    }
}
