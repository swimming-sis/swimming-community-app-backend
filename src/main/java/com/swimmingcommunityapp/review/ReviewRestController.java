package com.swimmingcommunityapp.review;

import com.swimmingcommunityapp.comment.request.CommentRequest;
import com.swimmingcommunityapp.comment.response.CommentCreateResponse;
import com.swimmingcommunityapp.comment.service.CommentService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/swimmingPools")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "4. 리뷰")
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
}
