package com.swimmingcommunityapp.post.service;

import com.swimmingcommunityapp.category.CategoryRepository;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.PostDto;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.post.request.PostModifyRequest;
import com.swimmingcommunityapp.post.response.PostDeleteResponse;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //게시물 작성
    public PostDto createPost(PostCreateRequest dto,String userName) {
        //userName 못찾을때 에러
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //저장
        Post post = Post.builder()
                .category(categoryRepository.findByName(dto.getCategory()))
                .title(dto.getTitle())
                .body(dto.getBody())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostDto.builder()
                .postId(savedPost.getId())
                .userId(savedPost.getUser().getId())
                .category(savedPost.getCategory().getName())
                .nickName(savedPost.getUser().getNickName())
                .userName(savedPost.getUser().getUserName())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .createdAt(savedPost.getCreatedAt())
                .lastModifiedAt(savedPost.getLastModifiedAt())
                .build();
    }

    //게시물 삭제
    @Transactional
    public PostDeleteResponse deletePost(Long postId, String userName) {
        //postId 없을때 에러 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //userName이 일치하지 않을때 에러 처리
        if (!Objects.equals(post.getUser().getUserName(),userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }
        //삭제
        postRepository.delete(post);

        return PostDeleteResponse.builder()
                .postId(postId)
                .userID(user.getId())
                .build();

    }

    //게시물 수정
    @Transactional
    public PostDto modifyPost(PostModifyRequest dto, Long postId, String userName){
        //postId 없을때 에러 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //userName이 일치하지 않을때 에러 처리
        if (!Objects.equals(post.getUser().getUserName(),userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        //수정
        post.updatePost(dto.getTitle(),dto.getBody());
        Post savedPost = postRepository.save(post);

        return PostDto.builder()
                .postId(savedPost.getId())
                .userId(savedPost.getUser().getId())
                .category(savedPost.getCategory().getName())
                .nickName(savedPost.getUser().getNickName())
                .userName(savedPost.getUser().getUserName())
                .title(savedPost.getTitle())
                .body(savedPost.getBody())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(savedPost.getLastModifiedAt())
                .build();
    }


    public PostDto detail(Long postId, String userName) {

        //postId 없을때 에러 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        return PostDto.fromEntity(post);

    }
}
