package com.swimmingcommunityapp.file.postFile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostFileCreateResponse {
    private List<String> uploadFileName;
    private List<String> S3StoredFileName;
    private String message;


    public static PostFileCreateResponse of(List<String> original, List<String> stored ) {
        return PostFileCreateResponse.builder()
                .uploadFileName(original)
                .S3StoredFileName(stored)
                .message("파일 첨부 완료")
                .build();
    }
}
