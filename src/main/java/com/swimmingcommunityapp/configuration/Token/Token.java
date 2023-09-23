package com.swimmingcommunityapp.configuration.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@RedisHash(value = "Token", timeToLive = 2592000)
public class Token {
    @Id
    private String id;

    private String refreshToken;

    @Indexed
    private String accessToken;

}
