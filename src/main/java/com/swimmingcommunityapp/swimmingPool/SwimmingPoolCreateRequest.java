package com.swimmingcommunityapp.swimmingPool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwimmingPoolCreateRequest {
    private Long uniqueNumber;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String phone;

}
