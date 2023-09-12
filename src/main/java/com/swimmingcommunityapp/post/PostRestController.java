package com.swimmingcommunityapp.post;

import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.request.PostModifyRequest;
import com.swimmingcommunityapp.post.response.PostCreateResponse;
import com.swimmingcommunityapp.post.response.PostDeleteResponse;
import com.swimmingcommunityapp.post.response.PostDetailResponse;
import com.swimmingcommunityapp.post.response.PostModifyResponse;
import com.swimmingcommunityapp.post.service.PostService;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Api(tags = "2. 게시판")
public class PostRestController {
    private final PostService postService;

    //게시물 등록
    @PostMapping
    @Operation(summary = "게시물 작성", description = "로그인 후, 게시물 등록")
    public Response<PostCreateResponse> create(@RequestBody PostCreateRequest dto, @ApiIgnore Authentication authentication) {
        PostDto postDto = postService.createPost(dto, authentication.getName());
        PostCreateResponse response = new PostCreateResponse(postDto.getUserId(), postDto.getPostId(), postDto.getCategory(), postDto.getUserName(), postDto.getNickName(), postDto.getTitle(), postDto.getBody(),postDto.getCreatedAt(),postDto.getLastModifiedAt());
        return Response.success(response);
    }

    //게시물 삭제
    @DeleteMapping("/{postId}/delete")
    @Operation(summary = "게시물 삭제", description = "로그인 후, 자신이 작성한 게시물만 삭제 가능")
    public Response<PostDeleteResponse> delete(@PathVariable Long postId,@ApiIgnore Authentication authentication) {
        return Response.success(postService.deletePost(postId, authentication.getName()));
    }

    //게시물 수정
    @PutMapping("/{postId}/modify")
    @Operation(summary = "게시물 수정", description = "로그인 후, 자신이 작성한 게시물만 수정 가능")
    public Response<PostModifyResponse> modify(@RequestBody PostModifyRequest dto, @PathVariable Long postId, @ApiIgnore Authentication authentication) {
        PostDto postDto = postService.modifyPost(dto,postId, authentication.getName());
        PostModifyResponse response = new PostModifyResponse(postDto.getUserId(), postDto.getPostId(), postDto.getCategory(), postDto.getUserName(), postDto.getNickName(), postDto.getTitle(), postDto.getBody(),postDto.getCreatedAt(),postDto.getLastModifiedAt());
        return Response.success(response);
    }

    //게시물 1개 조회
    @GetMapping("/{postId}/detail")
    @Operation(summary = "게시글 1개 조회", description = "게시물 클릭 후, 게시물 상세 내역 조회")
    public Response<PostDetailResponse> detail(@PathVariable Long postId, @ApiIgnore Authentication authentication){
        PostDto postDto = postService.detail(postId, authentication.getName());
        return Response.success(new PostDetailResponse(postDto.getUserId(), postDto.getPostId(), postDto.getCategory(), postDto.getUserName(), postDto.getNickName(), postDto.getTitle(), postDto.getBody(),postDto.getCreatedAt(),postDto.getLastModifiedAt()));
    }

}
