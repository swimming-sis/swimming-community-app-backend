package com.swimmingcommunityapp.post.controller;

import com.swimmingcommunityapp.post.response.PostDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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

    //게시물 전체 조회 (최신순, 10개마다 페이징처리)
    @GetMapping
    @Operation(summary = "게시글 전체 목록 조회", description = "게시물 목록 최신순으로 20개씩 조회")
    public Response<Page<PostDto>> pageable(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable){
        Page<PostDto> postDto = postService.pageList(pageable);
        return Response.success(postDto);
    }

    //내 게시물 목록 조회
    @GetMapping("/my")
    @Operation(summary = "내 게시물 목록 조회", description = "내 게시물만 보기")
    public Response<Page<PostDto>> myPostList(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC)Pageable pageable,@ApiIgnore Authentication authentication){
        Page<PostDto> postDto = postService.myPostList(pageable,authentication.getName());
        return Response.success(postDto);
    }

    // 검색 기능
    @GetMapping("/search")
    @Operation(summary = "검색 기능", description = "제목을 기준으로 검색 기능")
    public Response<Page<PostDto>> search(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC)Pageable pageable,@ApiIgnore Authentication authentication,String keyword){
        Page<PostDto> postDto = postService.searchPost(pageable,authentication.getName(),keyword);
        return Response.success(postDto);
    }

    @GetMapping("/{categoryName}")
    @Operation(summary = "카테고리별 게시물 목록 조회", description = "카테고리 별로 게시물을 조회할 수 있는 기능")
    public Response<Page<PostDto>> classify(@ApiIgnore Authentication authentication,@PathVariable String categoryName,@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC)Pageable pageable){
        Page<PostDto> postDto = postService.classifyList(authentication.getName(),categoryName,pageable);
        return Response.success(postDto);
    }

}
