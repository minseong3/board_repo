package com.cspi.notionboard.module.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private Long postId;
    private String commentPassword;
    private String content;
    private LocalDate createdAt;
}


