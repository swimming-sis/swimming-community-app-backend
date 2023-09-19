package com.swimmingcommunityapp.configuration.chekNumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash(value = "checkNumber",timeToLive = 180)
@AllArgsConstructor
@Builder
public class CheckNumber {
    @Id
    private Long id;

    @Indexed
    private String phoneNumber;

    private int randomNumber;

}
