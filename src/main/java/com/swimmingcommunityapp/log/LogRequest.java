package com.swimmingcommunityapp.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogRequest {
    private Long distance;
    private Long time;
    private Long calorie;
    private String contents;
    private String date;

}
