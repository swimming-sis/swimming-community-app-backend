package com.swimmingcommunityapp.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogRequest {
    private Long distance;
    private Long time;
    private Long calorie;

    private String contents;
}
