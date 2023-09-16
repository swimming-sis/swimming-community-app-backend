package com.swimmingcommunityapp.user.response;

import com.swimmingcommunityapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userName;
    private String nickName;
    private String phoneNumber;


    public static UserDto detailUser(User user){
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
