package com.cspi.notionboard.module.comment.service;

import com.cspi.notionboard.global.authentication.PasswordUtil;
import com.cspi.notionboard.global.exception.NotFoundException;
import com.cspi.notionboard.module.comment.dao.CommentMapper;
import com.cspi.notionboard.module.post.dao.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PostMapper postMapper;

    public List<Map<String, Object>> selectAllComments(Pageable pageable, Long postId) {
        return commentMapper.selectAllComments(pageable.getPageSize(), (int) pageable.getOffset(), postId);
    }

    public void insertComment(Long postId, Map<String, Object> comment) {
        log.info("postId: {}", postId);

        // <map> comment의 commentPassword를 해싱하여 <map> comment에 postId와 함께 저장
        log.info("입력된 comment_password: {}", comment.get("comment_password"));
        String hashedPassword = PasswordUtil.hashPassword((String) comment.get("comment_password"));
        log.info("hashedPassword after : {}", hashedPassword);

        comment.put("commentPassword", hashedPassword);
        comment.put("postId", postId);
        log.info("comment : {}", comment);
        commentMapper.insertComment(comment);
    }


    public void updateComment(Long postId, Long commentId, Map<String, Object> comment) {
        // 게시글 존재 유무 확인
        Map<String, Object> existingPost = postMapper.findByPostId(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 수정 할 comment를 commentId로 찾아 변수에 할당
        Map<String, Object> targetComment = commentMapper.findByCommentId(commentId);

        log.info("targetComment : {}", targetComment);
        log.info("(String) comment.get(\"comment_password\") : {}", comment.get("comment_password"));
        // 비밀번호 검증
        if(!PasswordUtil.checkPassword((String) comment.get("comment_password"), (String) targetComment.get("comment_password"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // <map> comment에 commentId put
        comment.put("commentId", commentId);
        commentMapper.updateComment(comment);
    }

    public void deleteComment(Long postId, Long commentId, String password) {
        // 게시글 존재 유무 확인
        Map<String, Object> existingPost = postMapper.findByPostId(postId);
        if(existingPost == null) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        // 코멘트ID로 코멘트 객체 변수에 할당
        Map<String, Object> targetComment = commentMapper.findByCommentId(commentId);

        log.info("(String)targetComment.get(\"comment_password\"): {}", (String)targetComment.get("comment_password"));
        // 비밀번호 검증
        if(!PasswordUtil.checkPassword(password, (String) targetComment.get("comment_password"))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentMapper.deleteComment(commentId);
    }
}
