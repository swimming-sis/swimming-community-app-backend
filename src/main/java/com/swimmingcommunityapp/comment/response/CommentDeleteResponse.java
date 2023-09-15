package com.swimmingcommunityapp.comment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDeleteResponse {
    private Long userId;
    private Long postId;
    private Long commentId;
}
