package com.swimmingcommunityapp.swimmingPool;

import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.response.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwimmngPoolResponse {
    private Long uniqueNumber;
    private String placeName;
    private Long distance;
    private String placeUrl;
    private String roadAddressName;
    private String phone;
    private Long x;
    private Long y;


    public static SwimmngPoolResponse fromEntity(SwimmingPool s) {
        return SwimmngPoolResponse.builder()
                .uniqueNumber(s.getUniqueNumber())
                .placeName(s.getPlaceName())
                .distance(s.getDistance())
                .placeUrl(s.getPlaceUrl())
                .roadAddressName(s.getRoadAddressName())
                .phone(s.getPhone())
                .x(s.getX())
                .y(s.getY())
                .build();
    }

}


