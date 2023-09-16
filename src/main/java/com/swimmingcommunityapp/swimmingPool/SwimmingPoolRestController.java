package com.swimmingcommunityapp.swimmingPool;

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
@RequestMapping("/api/v1/swimmingPools")
@RequiredArgsConstructor
@Api(tags = "3. 수영장")
public class SwimmingPoolRestController {

    private final SwimmingPoolService swimmingPoolService;

    //수영장 등록
    @PostMapping
    @Operation(summary = "수영장 등록", description = "리뷰 등록을 위해 수영장데이터 등록")
    public Response<Void> create(@RequestBody SwimmingPoolCreateRequest dto, @ApiIgnore Authentication authentication) {
        swimmingPoolService.createSwimmingPool(dto, authentication.getName());
        return Response.success();
    }




}
