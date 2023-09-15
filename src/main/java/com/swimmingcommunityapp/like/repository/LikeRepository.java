package com.swimmingcommunityapp.like.repository;

import com.swimmingcommunityapp.like.entity.Like;
import com.swimmingcommunityapp.post.entity.Post;
import com.swimmingcommunityapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

}
