package com.swimmingcommunityapp.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Long id;
    private String nickName;
    private String userName;
    private String phoneNumber;
}
