package com.swimmingcommunityapp.review;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.swimmingPool.SwimmingPool;
import com.swimmingcommunityapp.swimmingPool.SwimmingPoolRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final SwimmingPoolRepository swimmingPoolRepository;

    //리뷰 작성
    public ReviewDto createReview(ReviewRequest dto, Long swimmingPoolId, String userName) {
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //수영장 정보를 못찾을때 에러처리
        SwimmingPool swimmingPool = swimmingPoolRepository.findByUniqueNumber(swimmingPoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SWIMMINGPOOL_NOT_FOUND));

        Review review = Review.builder()
                .contents(dto.getContents())
                .user(foundUser)
                .swimmingPool(swimmingPool)
                .build();

       Review savedReview = reviewRepository.save(review);
        return ReviewDto.of(savedReview);
    }

    //리뷰 삭제
    public Boolean deleteReview(Long swimmingPoolId, Long reviewId, String userName) {
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
        //수영장 정보를 못찾을때 에러처리
        SwimmingPool swimmingPool = swimmingPoolRepository.findByUniqueNumber(swimmingPoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SWIMMINGPOOL_NOT_FOUND));

        //리뷰 정보를 못찾을때 에러처리
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        reviewRepository.delete(review);
        return true;
    }

    //리뷰 수정
    public ReviewDto modifyReview(ReviewRequest dto, Long swimmingPoolId, Long reviewId, String userName) {
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
        //수영장 정보를 못찾을때 에러처리
        SwimmingPool swimmingPool = swimmingPoolRepository.findByUniqueNumber(swimmingPoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SWIMMINGPOOL_NOT_FOUND));

        //리뷰 정보를 못찾을때 에러처리
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        review.updateReview(dto.getContents());
        Review savedReview = reviewRepository.save(review);

        return ReviewDto.of(savedReview);
    }

}
