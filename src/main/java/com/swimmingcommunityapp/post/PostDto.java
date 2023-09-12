package com.swimmingcommunityapp.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swimmingcommunityapp.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long postId;
    private Long userId;
    private String category;
    private String title;
    private String body;
    private String userName;
    private String nickName;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .userId(post.getUser().getId())
                .postId(post.getId())
                .category(post.getCategory().getName())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUser().getUserName())
                .nickName(post.getUser().getNickName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

}
