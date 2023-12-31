package com.swimmingcommunityapp.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swimmingcommunityapp.configuration.Token.TokenService;
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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "01. 회원 인증, 인가")
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final TokenService tokenService;

    //회원가입
    @Operation(summary = "회원가입", description = "sns을 이용하기 위한 회원 등록")
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest dto){
        return Response.success(userService.join(dto));
    }

    //로그인
    @Operation(summary = "일반 로그인", description = "로그인 시 token 발행")
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest dto){
        return Response.success(userService.login(dto));
    }

    //로그인
    @Operation(summary = "자동 로그인", description = "로그인 시 token 발행")
    @PostMapping("/autoLogin")
    public Response<UserLoginResponse> autoLogin(@RequestBody UserLoginRequest dto){
        return Response.success(userService.autoLogin(dto));
    }
    @Operation(summary = "로그아웃", description = "로그인 시 token 발행")
    @PostMapping("/logout")
    public Response<Void> logout(HttpServletRequest request, HttpServletResponse response,@ApiIgnore Authentication authentication,String accessToken){
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        tokenService.removeRefreshToken(accessToken);
        return Response.success();
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

    //계정 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "내 계정 삭제", description = "내 계정 정보  삭제하기")
    public Response<Boolean> delete(@ApiIgnore Authentication authentication){
        return Response.success(userService.deleteUser(authentication.getName()));
    }

    // 아이디 찾기
    @PostMapping("/account/id/find")
    @Operation(summary = "아이디 찾기", description = "핸드폰 번호로 아이디 찾기")
    public Response<String> findId(@RequestParam String phoneNumber){
        return Response.success(userService.findId(phoneNumber));
    }

    // 아이디 찾기
    @PostMapping("/account/password/find")
    @Operation(summary = "비밀번호 찾기", description = "아이디와 핸드폰 번호로 비밀번호 찾기")
    public Response<String> findPassword(@RequestParam String userName, @RequestParam String phoneNumber) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        return Response.success(userService.findPassword(userName,phoneNumber));
    }

}
