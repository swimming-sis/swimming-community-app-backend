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
    // 리뷰작성
    @PostMapping("/{swimmingPoolId}/reviews/write")
    @Operation(summary = "리뷰 작성", description = "로그인 후, 수영장 리뷰 작성")
    public Response<ReviewDto> create(@RequestBody ReviewRequest dto, @PathVariable Long swimmingPoolId, @ApiIgnore Authentication authentication){
        return Response.success(reviewService.createReview(dto, swimmingPoolId, authentication.getName()));
    }

}
