package com.cspi.notionboard.module.post.dto;

import com.cspi.notionboard.module.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {
        private Long postId;
        private String nickname;
        private String password;
        private String title;
        private String content;
        private LocalDate createdAt;
        private List<CommentDto> comments = new ArrayList<>();
}
