package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    //일지 작성
    public LogDto createLog(String userName,LogRequest dto){
        //userName 못찾을때 에러
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //저장
        Log log = Log.builder()
                .user(user)
                .calorie(dto.getCalorie())
                .contents(dto.getContents())
                .distance(dto.getDistance())
                .time(dto.getTime())
                .build();

        Log savedLog = logRepository.save(log);

        return LogDto.fromEntity(savedLog);
    }

    public String deleteLog(Long logId, String userName) {
        Log log = logRepository.findById(logId)
                .orElseThrow(() -> new AppException(ErrorCode.LOG_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //userName이 일치하지 않을때 에러 처리
        if (!Objects.equals(log.getUser().getUserName(),userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        logRepository.delete(log);
        return "삭제 성공";
    }
}