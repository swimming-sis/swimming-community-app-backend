package com.swimmingcommunityapp.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDto {
    private Long logId;
    private String nickName;
    private Long distance;
    private Long time;
    private Long calorie;
    private String contents;

    private String date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;

    public static  LogDto fromEntity(Log log){
        return  LogDto.builder()
                .logId(log.getId())
                .nickName(log.getUser().getNickName())
                .distance(log.getDistance())
                .time(log.getTime())
                .calorie(log.getCalorie())
                .contents(log.getContents())
                .date(log.getDate())
                .createdAt(log.getCreatedAt())
                .lastModifiedAt(log.getLastModifiedAt())
                .build();
    }

    public static Page<LogDto> toDto(Page<Log> log){
        Page<LogDto> logDto = log.map(l -> LogDto.builder()
                .logId(l.getId())
                .nickName(l.getUser().getNickName())
                .distance(l.getDistance())
                .time(l.getTime())
                .calorie(l.getCalorie())
                .contents(l.getContents())
                .date(l.getDate())
                .createdAt(l.getCreatedAt())
                .lastModifiedAt(l.getLastModifiedAt())
                .build());
        return logDto;
    }

}
