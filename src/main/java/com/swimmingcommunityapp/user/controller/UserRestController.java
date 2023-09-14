package com.swimmingcommunityapp.user.controller;

import com.swimmingcommunityapp.user.request.UserJoinRequest;
import com.swimmingcommunityapp.user.response.UserJoinResponse;
import com.swimmingcommunityapp.user.request.UserLoginRequest;
import com.swimmingcommunityapp.user.response.UserLoginResponse;
import com.swimmingcommunityapp.user.service.UserService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    //userName 조회
    @Operation(summary = "아이디 조회", description = "아이디 입력시 가입된 사용자 확인 절차")
    @GetMapping("/userName/{userName}")
    public Response<String> searchUserName(@PathVariable String userName){
        return Response.success(userService.searchUserName(userName));
    }

    //nickName 조회
    @Operation(summary = "닉네임 조회", description = "닉네임 입력시 가입된 사용자 확인 절차")
    @GetMapping("/nickName/{nickName}")
    public Response<String> searchNickName(@PathVariable String nickName){
        return Response.success(userService.searchNickName(nickName));
    }

    //phoneNumber 조회
    @Operation(summary = "핸드폰 번호 조회", description = "핸드폰번호 입력시 가입된 사용자 확인 절차")
    @GetMapping("phoneNumber/{phoneNumber}")
    public Response<String> searchPhoneNumber(@PathVariable String phoneNumber){
        return Response.success(userService.searchNickName(phoneNumber));
    }
}
