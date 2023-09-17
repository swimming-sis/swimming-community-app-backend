package com.swimmingcommunityapp.review;

import com.swimmingcommunityapp.comment.response.CommentDto;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/swimmingPools")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "6. 리뷰")
public class ReviewRestController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/{swimmingPoolId}/reviews/write")
    @Operation(summary = "리뷰 작성", description = "로그인 후, 수영장 리뷰 작성")
    public Response<ReviewDto> create(@RequestBody ReviewRequest dto, @PathVariable Long swimmingPoolId, @ApiIgnore Authentication authentication){
        return Response.success(reviewService.createReview(dto, swimmingPoolId, authentication.getName()));
    }

    //리뷰 삭제
    @DeleteMapping("/{swimmingPoolId}/reviews/{reviewId}/delete")
    @Operation(summary = "리뷰 삭제", description = "로그인 후, 수영장 리뷰 삭제")
    public Response<Boolean> delete(@PathVariable Long swimmingPoolId, @PathVariable Long reviewId,@ApiIgnore Authentication authentication){
        return Response.success(reviewService.deleteReview(swimmingPoolId, reviewId, authentication.getName()));
    }

    //리뷰 수정
    @PutMapping("/{swimmingPoolId}/reviews/{reviewId}/modify")
    @Operation(summary = "리뷰 수정", description = "로그인 후, 수영장 리뷰 수정")
    public Response<ReviewDto> modify(@RequestBody ReviewRequest dto, @PathVariable Long swimmingPoolId, @PathVariable Long reviewId, @ApiIgnore Authentication authentication){
        return Response.success(reviewService.modifyReview(dto,swimmingPoolId,reviewId,authentication.getName()));
    }

    //리뷰 1개 조회 기능
    @GetMapping("/{swimmingPoolId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 1개 조회", description = "로그인 후, 수영장 리뷰 1개 조회")
    public Response<ReviewDto> searchReview (@ApiIgnore Authentication authentication, @PathVariable Long swimmingPoolId, @PathVariable Long reviewId){
        return Response.success(reviewService.searchReview(authentication.getName(), swimmingPoolId, reviewId));
    }

    //수영장 별 리뷰 리스트 조회
    @GetMapping("/{swimmingPoolId}/reviews/")
    @Operation(summary = "수영장 별 리뷰 리스트 조회", description = "로그인 후, 수영장의 전체 리뷰 리스트")
    public Response<Page<ReviewDto>> pageable(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long swimmingPoolId,@ApiIgnore Authentication authentication){
        Page<ReviewDto> reviewDto = reviewService.pageList(pageable,swimmingPoolId,authentication.getName());
        return Response.success(reviewDto);
    }

    //내 리뷰 목록 조회
    @GetMapping("/reviews/my")
    @Operation(summary = "내 리뷰 목록 조회", description = "로그인 후, 내가 작성한 리뷰 목록을 조회")
    public Response<Page<ReviewDto>> myReviewList(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable,@ApiIgnore Authentication authentication){
        Page<ReviewDto> reviewDto = reviewService.myReviewList(pageable,authentication.getName());
        return Response.success(reviewDto);
    }
}
