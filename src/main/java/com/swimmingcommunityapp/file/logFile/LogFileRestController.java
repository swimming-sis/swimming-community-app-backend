package com.swimmingcommunityapp.file.logFile;


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
@RequestMapping("/api/v1/logs/")
@RequiredArgsConstructor
@Api(tags = "10. 일지 파일 업로드")
public class LogFileRestController {

    private final LogFileService logFileService;

    //파일 업로드
    @PostMapping(path="/{logId}/postFiles/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "일지 파일 업로드", description = "일지 작성시, 파일 업로드")
    public Response<LogFileUploadResponse> upload(@PathVariable Long logId, @RequestPart List<MultipartFile> multipartFile, @ApiIgnore Authentication authentication){
        return Response.success(logFileService.uploadFile(logId,multipartFile,authentication.getName()));
    }

    // S3 파일 삭제
    @Operation(summary = "일지 첨부파일 삭제", description = "일지 작성시, 업로드한 파일 삭제")
    @DeleteMapping("/{logId}/postFiles/{logFileId}/files/delete")
    public Response<String> delete(@PathVariable Long logId, @PathVariable Long logFileId, @ApiIgnore Authentication authentication, @RequestParam String filePath) {
        return Response.success(logFileService.deleteLogFile(logId,logFileId,filePath,authentication.getName()));
    }

}
