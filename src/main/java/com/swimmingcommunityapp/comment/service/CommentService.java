package com.swimmingcommunityapp.comment.service;

import com.swimmingcommunityapp.comment.entity.Comment;
import com.swimmingcommunityapp.comment.repository.CommentRepository;
import com.swimmingcommunityapp.comment.request.CommentRequest;
import com.swimmingcommunityapp.comment.response.CommentCreateResponse;
import com.swimmingcommunityapp.comment.response.CommentDeleteResponse;
import com.swimmingcommunityapp.comment.response.CommentDto;
import com.swimmingcommunityapp.comment.response.CommentModifyResponse;
import com.swimmingcommunityapp.exception.AppException;
import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.post.repository.PostRepository;
import com.swimmingcommunityapp.user.entity.User;
import com.swimmingcommunityapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //댓글 수정
    public CommentModifyResponse modifyComment(CommentRequest dto, Long postId, Long commentId, String userName) {
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

        //수정
        foundComment.setComment(dto.getComment());

        Comment savedComment = commentRepository.save(foundComment);

        return CommentModifyResponse.of(savedComment);
    }

    //게시물 댓글 목록 조회
    public Page<CommentDto> pageList(Pageable pageable,Long postId){
        Page<Comment>  comment = commentRepository.findByPostId(postId,pageable);
        Page<CommentDto> commentDto = CommentDto.toDto(comment);
        return commentDto;
    }

    //내 댓글 목록 조회
    public Page<CommentDto> myCommentList(Pageable pageable, String userName) {

        //userName 정보를 못찾을때 에러처리
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Comment>  comment = commentRepository.findByUserId(foundUser.getId(),pageable);
        Page<CommentDto> commentDto = CommentDto.toDto(comment);
        return commentDto;
    }
}
