package com.cspi.notionboard.module.answer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnswerDto {
    private Long answerId;
    private Long postId;
    private String answerPassword;
    private String content;
    private LocalDate createdAt;
}


