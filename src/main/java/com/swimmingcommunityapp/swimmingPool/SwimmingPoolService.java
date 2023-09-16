package com.swimmingcommunityapp.swimmingPool;


import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwimmingPoolService {

    private final SwimmingPoolRepository swimmingPoolRepository;
    private final UserRepository userRepository;

    // 수영장 데이터 등록
    public void createSwimmingPool(SwimmingPoolCreateRequest dto, String userName) {

        //userName 못찾을때 에러
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));


        //수영장 중복데이터 확인
        swimmingPoolRepository.findByUniqueNumber(dto.getUniqueNumber())
                .ifPresent(swimmingPool -> {
                    throw new AppException(ErrorCode.SWIMMINGPOOL_DUPLICATION);
                });

        //저장
        SwimmingPool swimmingPool = SwimmingPool.builder()
                .uniqueNumber(dto.getUniqueNumber())
                .placeName(dto.getPlaceName())
                .distance(dto.getDistance())
                .placeUrl(dto.getPlaceUrl())
                .roadAddressName(dto.getRoadAddressName())
                .phone(dto.getPhone())
                .x(dto.getX())
                .y(dto.getY())
                .build();

        swimmingPoolRepository.save(swimmingPool);
    }
}
