package com.cspi.notionboard.global.audit;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Auditable {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
