package com.swimmingcommunityapp.review;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ReviewDto {
    private Long reviewId;
    private Long userId;
    private String userName;
    private String nickName;
    private Long swimmingPoolId;
    private String contents;
    private Long ratingStar;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;

    public static ReviewDto of(Review review){
        return ReviewDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .nickName(review.getUser().getNickName())
                .userName(review.getUser().getUserName())
                .swimmingPoolId(review.getSwimmingPool().getUniqueNumber())
                .contents(review.getContents())
                .ratingStar(review.getRatingStar())
                .createdAt(review.getCreatedAt())
                .lastModifiedAt(review.getLastModifiedAt())
                .build();
    }


    public static Page<ReviewDto> toDto(Page<Review> review){
        Page<ReviewDto> reviewDto = review.map(r -> ReviewDto.builder()
                .reviewId(r.getId())
                .userId(r.getUser().getId())
                .userName(r.getUser().getUserName())
                .nickName(r.getUser().getNickName())
                .swimmingPoolId(r.getSwimmingPool().getId())
                .contents(r.getContents())
                .ratingStar(r.getRatingStar())
                .createdAt(r.getCreatedAt())
                .lastModifiedAt(r.getLastModifiedAt())
                .build());
        return reviewDto;
    }

}
