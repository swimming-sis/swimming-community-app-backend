package com.swimmingcommunityapp.comment.controller;

import com.swimmingcommunityapp.comment.request.CommentRequest;
import com.swimmingcommunityapp.comment.response.CommentCreateResponse;
import com.swimmingcommunityapp.comment.response.CommentDeleteResponse;
import com.swimmingcommunityapp.comment.response.CommentDto;
import com.swimmingcommunityapp.comment.response.CommentModifyResponse;
import com.swimmingcommunityapp.comment.service.CommentService;
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
    @PutMapping("/{postId}/comments/{commentId}/modify")
    @Operation(summary = "댓글 수정", description = "로그인 후, 댓글 수정")
    public Response<CommentModifyResponse> modify(@RequestBody CommentRequest dto, @PathVariable Long postId, @PathVariable Long commentId, @ApiIgnore Authentication authentication){
        return Response.success(commentService.modifyComment(dto, postId, commentId,authentication.getName()));
    }

    //댓글 조회
    @GetMapping("{postId}/comments")
    @Operation(summary = "게시물 댓글 목록 조회", description = "로그인 후, 게시물 댓글 목록 조회")
    public Response<Page<CommentDto>> pageable(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable,@PathVariable Long postId){
        Page<CommentDto> commentDto = commentService.pageList(pageable,postId);
        return Response.success(commentDto);
    }

    //내 댓글 목록 조회
    @GetMapping("/comments/my")
    @Operation(summary = "내 게시물 목록 조회", description = "내 게시물만 보기")
    public Response<Page<CommentDto>> myCommentList(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC)Pageable pageable, @ApiIgnore Authentication authentication){
        Page<CommentDto> commentDto = commentService.myCommentList(pageable,authentication.getName());
        return Response.success(commentDto);
    }

    //댓글 1개 조회
    @GetMapping("/comments/{commentId}")
    @Operation(summary = "댓글 1개 조회", description = "댓글 1개 조회")
    public Response<CommentDto> searchComment (@ApiIgnore Authentication authentication,@PathVariable Long commentId){
        return Response.success(commentService.searchComment(authentication.getName(), commentId));
    }
}
