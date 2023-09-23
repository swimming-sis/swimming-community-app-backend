package com.swimmingcommunityapp.like.controller;

import com.swimmingcommunityapp.like.service.LikeService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
@Api(tags = "04. 좋아요")
public class LikeRestController {

    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    @Operation(summary = "좋아요 누르기", description = "게시물의 좋아요 누르기")
    public Response<String> like(@PathVariable Long postId, @ApiIgnore Authentication authentication){
        return Response.success(likeService.addLike(postId, authentication.getName()));
    }

    @DeleteMapping ("/{postId}/likes")
    @Operation(summary = "좋아요 취소", description = "게시물의 좋아요 취소")
    public Response<String> deleteLike(@PathVariable Long postId, @ApiIgnore Authentication authentication){
        return Response.success(likeService.deleteLike(postId, authentication.getName()));

    }

    @GetMapping("/{postId}/likes")
    @Operation(summary = "좋아요 조회", description = "해당 게시물에 좋아요를 눌렀는지 확인")
    public Response<Boolean> searchLike(@PathVariable Long postId, @ApiIgnore Authentication authentication){
        return Response.success(likeService.searchLike(postId,authentication.getName()));
    }
}
