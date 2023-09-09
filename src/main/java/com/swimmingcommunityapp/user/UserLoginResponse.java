package com.swimmingcommunityapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private Long id;
    private String userName;
    private String nickName;
    private String phoneNumber;
    private String jwt;

    public UserLoginResponse(String token,Long id, String userName, String nickName, String phoneNumber) {
        this.jwt = token;
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }
}
