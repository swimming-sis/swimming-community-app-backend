package com.swimmingcommunityapp.post.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostDeleteResponse {
    private Long postId;
    private Long userID;
}
