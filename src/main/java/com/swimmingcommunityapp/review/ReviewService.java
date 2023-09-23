package com.swimmingcommunityapp.review;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.swimmingPool.SwimmingPool;
import com.swimmingcommunityapp.swimmingPool.SwimmingPoolRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .ratingStar(dto.getRatingStar())
                .contents(dto.getContents())
                .user(foundUser)
                .swimmingPool(swimmingPool)
                .tag(dto.getTag())
                .build();


        //리뷰 저장
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

        review.updateReview(dto.getContents(), dto.getRatingStar());
        Review savedReview = reviewRepository.save(review);

        return ReviewDto.of(savedReview);
    }

    //리뷰 1개 조회 기능
    public ReviewDto searchReview(String userName, Long swimmingPoolId, Long reviewId) {
        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //수영장 정보를 못찾을때 에러처리
        SwimmingPool swimmingPool = swimmingPoolRepository.findByUniqueNumber(swimmingPoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SWIMMINGPOOL_NOT_FOUND));

        //리뷰 정보를 못찾을때 에러처리
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewDto.of(review);

    }


    //수영장별 리뷰 리스트 조회
    public Page<ReviewDto> pageList(Pageable pageable, Long swimmingPoolId, String userName) {
        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //수영장 정보를 못찾을때 에러처리
        SwimmingPool swimmingPool = swimmingPoolRepository.findByUniqueNumber(swimmingPoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SWIMMINGPOOL_NOT_FOUND));

        Page<Review> reviews = reviewRepository.findBySwimmingPoolUniqueNumber(swimmingPool.getUniqueNumber(),pageable);
        Page<ReviewDto> reviewDto = ReviewDto.toDto(reviews);
        return reviewDto;

    }

    // 내 리뷰 목록 조회
    public Page<ReviewDto> myReviewList(Pageable pageable, String userName) {
        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Review> reviews = reviewRepository.findByUserId(user.getId(),pageable);

        Page<ReviewDto> reviewDto = ReviewDto.toDto(reviews);
        return reviewDto;

    }

}
