package com.cspi.notionboard.module.post.service;

import com.cspi.notionboard.global.PasswordUtil;
import com.cspi.notionboard.global.exception.NotFoundException;
import com.cspi.notionboard.module.post.dao.PostMapper;
import com.cspi.notionboard.module.post.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public void createPost(PostDto postDto) {
        // 비밀번호 해싱 후 저장
        postDto.setPassword(PasswordUtil.hashPassword(postDto.getPassword()));
        postMapper.insertPost(postDto);
    }

    public List<PostDto> getAllPosts() {
        return postMapper.selectAllPosts();
    }

    public PostDto getPost(Long postId) {
        return postMapper.selectPost(postId);
    }

    public void patchPost(Long postId, PostDto postDto) {
        PostDto existingPost = postMapper.findById(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 비밀번호 검증
        if(!PasswordUtil.checkPassword(postDto.getPassword(), existingPost.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.updatePost(postId, postDto);
    }

    public void deletePost(Long postId, String password) {
        PostDto existingPost = postMapper.findById(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 입력된 비밀번호가 DB에 저장된 비밀번호와 일치하는지 확인
        boolean isMatch = BCrypt.checkpw(password, existingPost.getPassword());

        log.info("BCrypt 검증 결과: {}", isMatch); // true여야 정상적으로 비교됨

        // 비밀번호 검증 (평문의 password를 받아 해싱과정을 거치고 비교)
        if(!PasswordUtil.checkPassword(password, existingPost.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        postMapper.deletePost(postId, password);
    }
}
