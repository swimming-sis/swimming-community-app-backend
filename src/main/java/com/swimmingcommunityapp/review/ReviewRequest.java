package com.swimmingcommunityapp.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String contents;
    private Long ratingStar;
    private String tag;
}
