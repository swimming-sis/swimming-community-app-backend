package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
                .date(dto.getDate())
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

    public LogDto modifyLog(LogRequest dto, Long logId, String userName) {
        //일지를 못찾으면 에러처리
        Log log = logRepository.findById(logId)
                .orElseThrow(() -> new AppException(ErrorCode.LOG_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //userName이 일치하지 않을때 에러 처리
        if (!Objects.equals(log.getUser().getUserName(),userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        log.updateLog(dto);
        Log savaedLog = logRepository.save(log);

        return LogDto.fromEntity(savaedLog);
    }

    public Page<LogDto> pageList(Pageable pageable, String userName) {
        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Log> log = logRepository.findByUserId(user.getId(),pageable);
        Page<LogDto> logDto = LogDto.toDto(log);
        return logDto;
    }

    public Page<LogDto> searchDateLog(Date date, String userName, Pageable pageable) {

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Log> log = logRepository.findByDateAndUserId(date,user.getId(),pageable);
        Page<LogDto> logDto = LogDto.toDto(log);
        return logDto;
    }
}
