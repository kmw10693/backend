package com.backend.moamoa.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostOneCommentResponse {

    private Long parentId;

    private Long commentId;

    private String content;

    private String username;

    private List<CommentsChildrenResponse> children = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    @QueryProjection
    public PostOneCommentResponse(Long parentId, Long commentId, String content, String username, LocalDateTime createDate, LocalDateTime updateDate) {
        this.parentId = parentId;
        this.commentId = commentId;
        this.content = content;
        this.username = username;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

}
