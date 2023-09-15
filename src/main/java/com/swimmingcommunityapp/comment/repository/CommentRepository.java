package com.swimmingcommunityapp.comment.repository;

import com.swimmingcommunityapp.comment.entity.Comment;
import com.swimmingcommunityapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);

}
