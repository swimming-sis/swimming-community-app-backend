package com.swimmingcommunityapp.user.controller;

import com.swimmingcommunityapp.user.UserJoinRequest;
import com.swimmingcommunityapp.user.UserJoinResponse;
import com.swimmingcommunityapp.user.UserLoginRequest;
import com.swimmingcommunityapp.user.UserLoginResponse;
import com.swimmingcommunityapp.user.service.UserService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "1. 회원 인증, 인가")
public class UserRestController {

    private final UserService userService;

    //회원가입
    @Operation(summary = "회원가입", description = "sns을 이용하기 위한 회원 등록")
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest dto){
        return Response.success(userService.join(dto));
    }

    //로그인
    @Operation(summary = "로그인", description = "로그인 시 token 발행")
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest dto){
        return Response.success(userService.login(dto));
    }
}
