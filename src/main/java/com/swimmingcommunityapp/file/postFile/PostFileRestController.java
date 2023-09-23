package com.swimmingcommunityapp.file.postFile;

import com.swimmingcommunityapp.file.postFile.dto.PostFileCreateResponse;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/")
@RequiredArgsConstructor
@Api(tags = "09. 게시물 파일 업로드")
public class PostFileRestController {

    private final PostFileService postFileService;

    //파일 업로드
    @PostMapping(path="/{postId}/postFiles/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시판 파일 업로드", description = "게시물 작성시, 파일 업로드")
    public Response<PostFileCreateResponse> upload(@PathVariable Long postId, @RequestPart List<MultipartFile> multipartFile, @ApiIgnore Authentication authentication){
        return Response.success(postFileService.uploadFile(postId,multipartFile,authentication.getName()));
    }

    //파일 삭제
    // S3 파일 삭제
    @Operation(summary = "게시판 첨부파일 삭제", description = "게시물 작성시, 업로드한 파일 삭제")
    @DeleteMapping("/{postId}/postFiles/{postFileId}/delete")
    public Response<String> delete(@PathVariable Long postId, @PathVariable Long postFileId, @ApiIgnore Authentication authentication, @RequestParam String filePath) {
        return Response.success(postFileService.deletePostFile(postId,postFileId,filePath,authentication.getName()));
    }


}
