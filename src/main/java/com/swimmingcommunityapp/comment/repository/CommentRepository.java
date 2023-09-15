package com.swimmingcommunityapp.comment.repository;

import com.swimmingcommunityapp.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
