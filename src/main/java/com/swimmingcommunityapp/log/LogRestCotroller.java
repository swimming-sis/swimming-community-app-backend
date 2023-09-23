package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.response.Response;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

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

    @PutMapping("/{logId}/modify")
    @Operation(summary = "일지 수정", description = "로그인 후, 자신이 작성한 일지만 수정 가능")
    public Response<LogDto> modify(@RequestBody LogRequest dto, @PathVariable Long logId, @ApiIgnore Authentication authentication) {
        return Response.success(logService.modifyLog(dto,logId, authentication.getName()));
    }


    @GetMapping
    @Operation(summary = "일지 전체 목록 조회", description = "게시물 목록 최신순으로 10개씩 조회")
    public Response<Page<LogDto>> pageable(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable,@ApiIgnore Authentication authentication){
        Page<LogDto> logDto = logService.pageList(pageable, authentication.getName());
        return Response.success(logDto);
    }

    @GetMapping("/{date}")
    @Operation(summary = "날짜별 조회", description = "로그인 후, 날짜별 자신이 작성한 일지 조회")
    public Response<Page<LogDto>> search(@PageableDefault(sort = "createdAt",size = 10,direction = Sort.Direction.DESC) Pageable pageable, @PathVariable String date, @ApiIgnore Authentication authentication) {
        return Response.success(logService.searchDateLog(date, authentication.getName(),pageable));
    }


}
