package com.swimmingcommunityapp.comment.service;

import com.swimmingcommunityapp.comment.entity.Comment;
import com.swimmingcommunityapp.comment.repository.CommentRepository;
import com.swimmingcommunityapp.comment.request.CommentRequest;
import com.swimmingcommunityapp.comment.response.CommentCreateResponse;
import com.swimmingcommunityapp.comment.response.CommentDeleteResponse;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //댓글작성

    public CommentCreateResponse createComment(CommentRequest dto, Long postId, String userName) {
        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //저장
        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .user(foundUser)
                .post(foundPost)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentCreateResponse.of(savedComment);
    }

    //댓글 삭제
    public CommentDeleteResponse deleteComment(Long postId, Long commentId, String userName) {

        //commentId가 없을 때
        Comment foundComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        //postId 없을때 에러 처리
        Post foundPost = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        //userName이 일치하지 않을때 에러 처리
        if (!Objects.equals(foundPost.getUser().getUserName(), userName)) {
            throw new AppException(ErrorCode.INVALID_PERMISSION);
        }

        //삭제
        commentRepository.delete(foundComment);

        return CommentDeleteResponse.builder()
                .postId(postId)
                .userId(foundUser.getId())
                .commentId(commentId)
                .build();
    }
}