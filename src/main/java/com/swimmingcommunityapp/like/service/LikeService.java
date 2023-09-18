package com.swimmingcommunityapp.like.service;

import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.like.entity.Like;
import com.swimmingcommunityapp.like.repository.LikeRepository;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.post.response.PostDto;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Boolean addLike(Long postId, String userName) {
        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //좋아요 중복체크 확인
        likeRepository.findByUserAndPost(foundUser, foundPost)
                .ifPresent(like -> {
                    throw new AppException(ErrorCode.LIKE_DUPLICATION);
                });

        //좋아요 저장
        Like savedlike = Like.createLike(foundUser,foundPost);
        likeRepository.save(savedlike);

        updateLikeCnt(postId,1l);

        return true;
    }


    public Boolean deletreLike(Long postId, String userName) {
        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //좋아요 중복체크 되면 삭제
        likeRepository.findByUserAndPost(foundUser, foundPost)
                .ifPresent((a) -> {
                    likeRepository.delete(a);
                });

        updateLikeCnt(postId,-1l);

        return true;
    }

    public void updateLikeCnt(Long postId, Long cnt) {
        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        foundPost.updateLike(foundPost.getLikeCnt(),cnt);

        postRepository.save(foundPost);

    }

    public Boolean searchLike(Long postId, String userName) {
        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));


        //좋아요 중복체크 확인
        return likeRepository.findByUserAndPost(foundUser, foundPost).isPresent();
    }
}
