package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.response.PostCreateResponse;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
