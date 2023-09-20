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
    private String placeUrl;
    private String roadAddressName;
    private String phone;


    public static SwimmngPoolResponse fromEntity(SwimmingPool s) {
        return SwimmngPoolResponse.builder()
                .uniqueNumber(s.getUniqueNumber())
                .placeName(s.getPlaceName())
                .placeUrl(s.getPlaceUrl())
                .roadAddressName(s.getRoadAddressName())
                .phone(s.getPhone())
                .build();
    }

}


