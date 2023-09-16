package com.swimmingcommunityapp.swimmingPool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SwimmingPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "swimming_pool_id")
    private Long id;

    private Long uniqueNumber;
    private String placeName;

    private Long distance;
    private String placeUrl;
    private String roadAddressName;
    private String phone;
    private Long x;
    private Long y;

}
