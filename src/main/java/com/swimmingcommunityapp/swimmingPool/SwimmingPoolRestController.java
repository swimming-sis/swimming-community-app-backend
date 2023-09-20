package com.swimmingcommunityapp.swimmingPool;

import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.response.PostCreateResponse;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/swimmingPools")
@RequiredArgsConstructor
@Api(tags = "5. 수영장")
public class SwimmingPoolRestController {

    private final SwimmingPoolService swimmingPoolService;

    //수영장 등록
    @PostMapping
    @Operation(summary = "수영장 등록", description = "리뷰 등록을 위해 수영장데이터 등록")
    public Response<String> create(@RequestBody SwimmingPoolCreateRequest dto, @ApiIgnore Authentication authentication) {
        return Response.success(swimmingPoolService.createSwimmingPool(dto, authentication.getName()));
    }

    //수영장 조회
    @GetMapping("/{uniqueNumber}")
    @Operation(summary = "수영장 조회", description = "수영장 정보 조회")
    public Response<SwimmngPoolResponse> detail(@PathVariable Long uniqueNumber, @ApiIgnore Authentication authentication){
        return Response.success(swimmingPoolService.detailSwimmingPool(uniqueNumber, authentication.getName()));
    }



}
