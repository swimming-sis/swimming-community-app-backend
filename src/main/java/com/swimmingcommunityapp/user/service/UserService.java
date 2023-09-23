package com.swimmingcommunityapp.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swimmingcommunityapp.configuration.Token.TokenService;
import com.swimmingcommunityapp.sms.MessageDto;
import com.swimmingcommunityapp.sms.SmsService;
import com.swimmingcommunityapp.user.request.UserJoinRequest;
import com.swimmingcommunityapp.user.request.UserModifyRequest;
import com.swimmingcommunityapp.user.response.UserDto;
import com.swimmingcommunityapp.user.response.UserJoinResponse;
import com.swimmingcommunityapp.user.request.UserLoginRequest;
import com.swimmingcommunityapp.user.response.UserLoginResponse;
import com.swimmingcommunityapp.user.repository.UserRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final SmsService smsService;
    private final TokenService tokenService;

    @Value("${jwt.token.secret}")
    private String key;



    public UserJoinResponse join(UserJoinRequest dto) {

        //nickName 중복체크
        userRepository.findByNickName(dto.getNickName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.NICKNAME_DUPLICATION);
                });
        //userName 중복체크
        userRepository.findByUserName(dto.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATION);
                });

        //저장
        String securityPassword = encoder.encode(dto.getPassword());
        User savedUser = userRepository.save(dto.toEntity(securityPassword));

        return new UserJoinResponse(savedUser.getId(), savedUser.getNickName(), savedUser.getUserName(), savedUser.getPhoneNumber());

    }


    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
    }

    public UserLoginResponse login(UserLoginRequest dto) {
        //username 없음
        User selectedUser = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //password 틀림
        if (!encoder.matches(dto.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        //토큰 발행
        String token = JwtTokenUtil.createAccessToken(selectedUser.getUserName(), key);

        return new UserLoginResponse(token, selectedUser.getId(), selectedUser.getUserName(), selectedUser.getNickName(), selectedUser.getPhoneNumber());

    }

    public UserLoginResponse autoLogin(UserLoginRequest dto) {
        //username 없음
        User selectedUser = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //password 틀림
        if (!encoder.matches(dto.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        //access token 토큰 발행
        String accessToken = JwtTokenUtil.createAccessToken(selectedUser.getUserName(), key);
        String refreshToken = JwtTokenUtil.createRefreshToken(selectedUser.getUserName(), key);

        //redis에 저장
        tokenService.saveToken(dto.getUserName(),refreshToken,accessToken);

        // 토큰 만료시, crate해서 로그인 유지
        if (JwtTokenUtil.isExpired(accessToken,key)==false) {
            return new UserLoginResponse(accessToken, selectedUser.getId(), selectedUser.getUserName(), selectedUser.getNickName(), selectedUser.getPhoneNumber());
        } else if (JwtTokenUtil.isExpired(accessToken,key)==true) {
            String newAccessToken = JwtTokenUtil.createAccessToken(selectedUser.getUserName(),key);
            return new UserLoginResponse(newAccessToken, selectedUser.getId(), selectedUser.getUserName(), selectedUser.getNickName(), selectedUser.getPhoneNumber());
        } else if (JwtTokenUtil.isExpired(refreshToken,key)==true) {
            tokenService.removeRefreshToken(accessToken);
        }
        return null;
    }



    public Boolean searchUserName(String userName)  {
        return userRepository.findByUserName(userName).isPresent();
    }

    public Boolean searchNickName(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    public Boolean searchPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    //유저 조회
    public UserDto detailUser(String userName) {
        //username 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));


        return UserDto.detailUser(selectedUser);
    }

    //유저 수정
    public UserDto modifyUser(UserModifyRequest dto, String userName) {

        //username 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));


        String securityNewPassword = encoder.encode(dto.getPassword());
        selectedUser.updateUser(dto.getNickName(),dto.getPhoneNumber(),securityNewPassword);
        User savedUser = userRepository.save(selectedUser);

        return UserDto.detailUser(savedUser);
    }

    //회원 탈퇴
    public Boolean deleteUser(String userName) {

        //username 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        userRepository.delete(selectedUser);

        return true;
    }

    //아이디 찾기
    public String findId(String phoneNumber) {
        //핸드폰 번호 등록되어있는지 확인
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new AppException(ErrorCode.PHONENUMBER_NOT_FOUND));

        return nameMasking(user.getUserName());
    }

    //비밀번호 찾기
    public String findPassword(String userName, String phoneNumber) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        //아이디와 핸드폰번호가 일치하는 사용자 찾기
        User user = userRepository.findByPhoneNumberAndUserName(phoneNumber,userName)
                .orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        MessageDto messageDto = MessageDto.builder()
                .to(phoneNumber)
                .build();
        //패스워드 설정
        String changePassword = makeChangePassword();
        smsService.sendPassword(messageDto,changePassword);

        user.updatePassword(encoder.encode(changePassword));

        //저장
        userRepository.save(user);

        return "새 임시 비밀번호 전송 완료";
    }

    public String makeChangePassword(){
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();

        // UUID의 Most Significant Bits와 Least Significant Bits를 조합하여 16진수 문자열로 변환
        String combinedHex = Long.toHexString(mostSignificantBits) + Long.toHexString(leastSignificantBits);

        // 조합된 16진수 문자열에서 앞의 8글자만 추출
        String shortUUID = combinedHex.substring(0, 8);

        return shortUUID;
    }

    public String nameMasking(String userName) {
        if (userName.length() <=9) {
            return userName.substring(0,3)+"****";
        } else if (userName.length() <= 13) {
            return userName.substring(0,4)+"****";
        }else{
            return userName.substring(0,6)+"****";
        }
    }
}
