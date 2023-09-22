package com.swimmingcommunityapp.file.postFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.file.postFile.dto.PostFileCreateResponse;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final AmazonS3Client amazonS3Client;
    private final PostFileRepository postFileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PostFileCreateResponse uploadFile(Long postId, List<MultipartFile> multipartFile, String userName) {
        // 빈 파일이 아닌지 확인, 파일 자체를 첨부안하거나 첨부해도 내용이 비어있는지 확인 - 비어있으면 FILE_NOT_EXISTS 에러발생
        validateFileExists(multipartFile);

        //postId 없을때 에러 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));



        // 원본 파일 이름, S3에 저장될 파일 이름 리스트
        List<String> originalFileNameList = new ArrayList<>();
        List<String> storedFileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            String originalFilename = file.getOriginalFilename();

            int index;
            // file 형식이 잘못된 경우를 확인
            try {
                index = originalFilename.lastIndexOf(".");
            } catch (StringIndexOutOfBoundsException e) {
                throw new AppException(ErrorCode.WRONG_FILE_FORMAT);
            }

            String ext = originalFilename.substring(index + 1);

            // 저장될 파일 이름
            String storedFileName = UUID.randomUUID() + "." + ext;

            // 저장할 디렉토리 경로 + 파일 이름
            String key = "swimming-community-app/" + storedFileName;

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
            }

            String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
            PostFile postFile = PostFile.makePostFile(originalFilename, storeFileUrl, post);
            postFileRepository.save(postFile);

            storedFileNameList.add(storedFileName);
            originalFileNameList.add(originalFilename);
        });

        return PostFileCreateResponse.of(originalFileNameList, storedFileNameList);
    }


    // 빈 파일이 아닌지 확인, 파일 자체를 첨부안하거나 첨부해도 내용이 비어있는지 확인 - 비어있으면 FILE_NOT_EXISTS 에러발생
    private void validateFileExists(List<MultipartFile> multipartFile) {
        for (MultipartFile mf : multipartFile) {
            if (mf.isEmpty()) {
                throw new AppException(ErrorCode.FILE_NOT_EXISTS);
            }
        }
    }
}
