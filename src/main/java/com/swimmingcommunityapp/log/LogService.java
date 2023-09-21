package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
