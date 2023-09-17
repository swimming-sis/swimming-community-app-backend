package com.swimmingcommunityapp.user.controller;

import com.swimmingcommunityapp.user.request.UserJoinRequest;
import com.swimmingcommunityapp.user.request.UserModifyRequest;
import com.swimmingcommunityapp.user.response.UserDto;
import com.swimmingcommunityapp.user.response.UserJoinResponse;
import com.swimmingcommunityapp.user.request.UserLoginRequest;
import com.swimmingcommunityapp.user.response.UserLoginResponse;
import com.swimmingcommunityapp.user.service.UserService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "1. 회원 인증, 인가")
@Slf4j
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
    @GetMapping("/userName")
    public Response<Boolean> searchUserName(@RequestParam String userName) {
        return Response.success(userService.searchUserName(userName));
    }

    //nickName 조회
    @Operation(summary = "닉네임 조회", description = "닉네임 입력시 가입된 사용자 확인 절차")
    @GetMapping("/nickName")
    public Response<Boolean> searchNickName(@RequestParam String nickName){
        return Response.success(userService.searchNickName(nickName));
    }

    //phoneNumber 조회
    @Operation(summary = "핸드폰 번호 조회", description = "핸드폰번호 입력시 가입된 사용자 확인 절차")
    @GetMapping("/phoneNumber")
    public Response<Boolean> searchPhoneNumber(@RequestParam String phoneNumber){
        return Response.success(userService.searchPhoneNumber(phoneNumber));
    }

    //계정 정보 조회
    @GetMapping
    @Operation(summary = "내 계정 조회", description = "내 계정 정보 조회 하기")
    public Response<UserDto> detail(@ApiIgnore Authentication authentication){
        return Response.success(userService.detailUser(authentication.getName()));
    }

    //계정 정보 수정
    @PutMapping("/modify")
    @Operation(summary = "내 계정 수정", description = "내 계정 정보  수정하기")
    public Response<UserDto> modify(@RequestBody UserModifyRequest dto, @ApiIgnore Authentication authentication){
        return Response.success(userService.modifyUser(dto, authentication.getName()));
    }
}
