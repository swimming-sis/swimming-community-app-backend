package com.swimmingcommunityapp.post.request;

import com.swimmingcommunityapp.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModifyRequest {
    private String title;
    private String body;

}
