package com.swimmingcommunityapp.comment.controller;

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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "3. 댓글")
public class CommentRestController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/{postId}/comments/write")
    @Operation(summary = "댓글 작성", description = "로그인 후, 댓글 작성")
    public Response<CommentCreateResponse> create(@RequestBody CommentRequest dto, @PathVariable Long postId, @ApiIgnore Authentication authentication){
        return Response.success(commentService.createComment(dto, postId, authentication.getName()));
    }



}
