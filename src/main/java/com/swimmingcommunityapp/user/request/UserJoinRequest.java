package com.swimmingcommunityapp.user.request;

import com.swimmingcommunityapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoinRequest {
    private String userName;
    private String nickName;
    private String phoneNumber;
    private String password;

    public User toEntity(String securityPassword) {
        return User.builder()
                .userName(this.userName)
                .nickName(this.nickName)
                .phoneNumber(this.phoneNumber)
                .password(securityPassword)
                .build();
    }
}
