package com.swimmingcommunityapp.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@Api(tags = "7. 핸드폰 인증")
public class SmsRestController {
    private final SmsService smsService;

    @PostMapping
    @Operation(summary = "핸드폰 인증 메세지 발송", description = "회원가입 시, 유효한 사용자 인증을 위해 핸드폰 인증 메세지 발송")
    public Response<SmsResponse> send(@RequestBody MessageDto messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return Response.success(smsService.sendSms(messageDto));
    }

    @PostMapping ("/api/v1/users/phoneNumber/check")
    @Operation(summary = "핸드폰 인증 확인", description = "회원가입 시, 유효한 사용자 인증을 위해 핸드폰 인증 번호 일치 여부")
    public Response<Boolean> check(@RequestBody CheckPhoneNumberRequest dto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return Response.success(smsService.checkSms(dto.getPhoneNumber(),dto.getCheckNumber()));
    }
}
