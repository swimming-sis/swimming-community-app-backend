package com.swimmingcommunityapp.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.yaml.snakeyaml.events.Event;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Long id;
    private String nickName;
    private String userName;
    private String phoneNumber;
}
