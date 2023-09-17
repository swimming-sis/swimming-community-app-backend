package com.swimmingcommunityapp.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModifyRequest {
    private String nickName;
    private String phoneNumber;
    private String password;

}
