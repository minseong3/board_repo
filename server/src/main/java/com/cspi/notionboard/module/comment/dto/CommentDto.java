package com.cspi.notionboard.module.comment.dto;

import com.cspi.notionboard.global.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto extends Auditable {
    private Long commentId;
    private Long postId;
    private String commentPassword;
    private String content;
}


