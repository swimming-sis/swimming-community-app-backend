package com.swimmingcommunityapp.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequest {
    private String title;
    private String body;
    private String category;

}
