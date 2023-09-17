package com.swimmingcommunityapp.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private Long userId;
    private Long swimmingPoolId;
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;

    public static ReviewDto of(Review review){
        return ReviewDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .swimmingPoolId(review.getSwimmingPool().getUniqueNumber())
                .contents(review.getContents())
                .createdAt(review.getCreatedAt())
                .lastModifiedAt(review.getLastModifiedAt())
                .build();
    }
}
