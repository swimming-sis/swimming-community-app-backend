package com.swimmingcommunityapp.comment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swimmingcommunityapp.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long userId;
    private Long postId;
    private Long commentId;
    private String comment;
    private String userName;
    private String nickName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;




    public static Page<CommentDto> toDto(Page<Comment> comment) {
        Page<CommentDto> commentDto = comment.map(c -> CommentDto.builder()
                .userId(c.getUser().getId())
                .postId(c.getPost().getId())
                .commentId(c.getId())
                .comment(c.getComment())
                .userName(c.getUser().getUserName())
                .nickName(c.getUser().getNickName())
                .createdAt(c.getCreatedAt())
                .lastModifiedAt(c.getLastModifiedAt())
                .build());
        return commentDto;

    }
}
