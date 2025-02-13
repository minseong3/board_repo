package com.cspi.notionboard.module.post.dto;

import com.cspi.notionboard.global.audit.Auditable;
import com.cspi.notionboard.module.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto extends Auditable {
        private Long postId;
        private String nickname;
        private String password;
        private String title;
        private String content;
        private List<CommentDto> comments = new ArrayList<>();
}
