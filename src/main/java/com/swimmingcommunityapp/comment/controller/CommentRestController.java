package com.swimmingcommunityapp.comment.controller;

import com.swimmingcommunityapp.comment.request.CommentRequest;
import com.swimmingcommunityapp.comment.response.CommentCreateResponse;
import com.swimmingcommunityapp.comment.response.CommentDeleteResponse;
import com.swimmingcommunityapp.comment.response.CommentModifyResponse;
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

    //댓글 삭제
    @DeleteMapping("/{postsId}/comments/{commentId}/delete")
    @Operation(summary = "댓글 삭제", description = "로그인 후, 댓글 삭제")
    public Response<CommentDeleteResponse> delete(@PathVariable Long postsId, @PathVariable Long commentId, @ApiIgnore Authentication authentication){
        return Response.success(commentService.deleteComment(postsId, commentId, authentication.getName()));
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "로그인 후, 댓글 수정")
    public Response<CommentModifyResponse> modify(@RequestBody CommentRequest dto, @PathVariable Long postId, @PathVariable Long commentId, @ApiIgnore Authentication authentication){
        return Response.success(commentService.modifyComment(dto, postId, commentId,authentication.getName()));
    }

}
