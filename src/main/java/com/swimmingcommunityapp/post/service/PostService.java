package com.swimmingcommunityapp.post.service;

import com.swimmingcommunityapp.category.CategoryRepository;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.PostCreateRequest;
import com.swimmingcommunityapp.post.PostCreateResponse;
import com.swimmingcommunityapp.post.PostDto;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //포스트 작성
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

}
