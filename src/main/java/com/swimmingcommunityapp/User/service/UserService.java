package com.swimmingcommunityapp.User.service;

import com.swimmingcommunityapp.User.UserJoinRequest;
import com.swimmingcommunityapp.User.UserJoinResponse;
import com.swimmingcommunityapp.User.repository.UserRepository;
import com.swimmingcommunityapp.User.entity.User;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


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

        return  new UserJoinResponse(savedUser.getId(),savedUser.getNickName(),savedUser.getUserName(), savedUser.getPhoneNumber());

    }


    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
    }

}
