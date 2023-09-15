package com.swimmingcommunityapp.user.service;

import com.swimmingcommunityapp.user.request.UserJoinRequest;
import com.swimmingcommunityapp.user.response.UserJoinResponse;
import com.swimmingcommunityapp.user.request.UserLoginRequest;
import com.swimmingcommunityapp.user.response.UserLoginResponse;
import com.swimmingcommunityapp.user.repository.UserRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 300l;

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
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs);

        return new UserLoginResponse(token, selectedUser.getId(), selectedUser.getUserName(), selectedUser.getNickName(), selectedUser.getPhoneNumber());

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
}
