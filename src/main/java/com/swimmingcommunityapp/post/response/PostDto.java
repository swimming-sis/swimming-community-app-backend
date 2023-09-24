package com.swimmingcommunityapp.post.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swimmingcommunityapp.post.entity.Post;
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
public class PostDto {
    private Long postId;
    private Long userId;
    private String category;
    private String title;
    private String body;
    private String userName;
    private String nickName;
    private Long likeCnt;
    private Long commentCnt;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
                .likeCnt(post.getLikeCnt())
                .commentCnt(post.getCommentCnt())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

    public static Page<PostDto> toDto(Page<Post> post){
        Page<PostDto> postDto = post.map(p -> PostDto.builder()
                .postId(p.getId())
                .userId(p.getUser().getId())
                .category(p.getCategory().getName())
                .title(p.getTitle())
                .body(p.getBody())
                .userName(p.getUser().getUserName())
                .nickName(p.getUser().getNickName())
                .likeCnt(p.getLikeCnt())
                .commentCnt(p.getCommentCnt())
                .createdAt(p.getCreatedAt())
                .lastModifiedAt(p.getLastModifiedAt())
                .build());
        return postDto;
    }


}
