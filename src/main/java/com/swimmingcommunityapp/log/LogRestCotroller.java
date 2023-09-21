package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.request.PostModifyRequest;
import com.swimmingcommunityapp.post.response.PostCreateResponse;
import com.swimmingcommunityapp.post.response.PostDeleteResponse;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.post.response.PostModifyResponse;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@Api(tags = "8. 일지")
public class LogRestCotroller {

    private final LogService logService;


    @PostMapping("/write")
    @Operation(summary = "일지 작성", description = "로그인 후, 일지 등록")
    public Response<LogDto> create(@RequestBody LogRequest dto, @ApiIgnore Authentication authentication) {
        return Response.success(logService.createLog(authentication.getName(),dto));
    }

    @DeleteMapping("/{logId}/delete")
    @Operation(summary = "일지 삭제", description = "로그인 후, 자신이 작성한 일지만 삭제 가능")
    public Response<String> delete(@PathVariable Long logId, @ApiIgnore Authentication authentication) {
        logService.deleteLog(logId, authentication.getName());
        return Response.success(logService.deleteLog(logId, authentication.getName()));
    }

}
