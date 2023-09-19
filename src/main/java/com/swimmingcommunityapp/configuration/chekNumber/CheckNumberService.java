package com.swimmingcommunityapp.configuration.chekNumber;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckNumberService {
    private final CheckNumberRepository checkNumberRepository;

    @Transactional
    public void saveNumber(String phoneNumber, int randomNumber){

        checkNumberRepository.save(CheckNumber.builder()
                        .phoneNumber(phoneNumber)
                        .randomNumber(randomNumber)
                .build());

    }

    public int checkNumber(String phoneNumber) {
        return checkNumberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.PHONENUMBER_NOT_FOUND))
                .getRandomNumber();

    }
}
