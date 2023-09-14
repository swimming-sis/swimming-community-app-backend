package com.swimmingcommunityapp.post.service;

import com.swimmingcommunityapp.category.CategoryRepository;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.request.PostCreateRequest;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.post.request.PostModifyRequest;
import com.swimmingcommunityapp.post.response.PostDeleteResponse;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //게시글 상세 조회
    public PostDto detail(Long postId, String userName) {

        //postId 없을때 에러 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        return PostDto.fromEntity(post);

    }

    //게시글 전체 조회
    public Page<PostDto> pageList(Pageable pageable){
        Page<Post>  post = postRepository.findAll(pageable);
        Page<PostDto> postDto = PostDto.toDto(post);
        return postDto;
    }

    //내 게시물 목록 조회
    public Page<PostDto> myPostList(Pageable pageable,String userName){

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));


        Page<Post>  post = postRepository.findByUserId(user.getId(), pageable);
        Page<PostDto> postDto = PostDto.toDto(post);
        return postDto;
    }

    //검색 기능
    public Page<PostDto> searchPost(Pageable pageable, String userName,String keyword) {

        //userName 정보를 못찾을때 에러처리
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Post>  post = postRepository.findByTitleContaining(keyword,pageable);
        Page<PostDto> postDto = PostDto.toDto(post);
        return postDto;
    }

//    // 카테고리 별 리스트
//    public List<Post> classifyList(String userName, Category category) {
//
//        //userName 정보를 못찾을때 에러처리
//        User user = userRepository.findByUserName(userName)
//                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
//
//        return postRepository.findByCategory(category);
//    }

}
