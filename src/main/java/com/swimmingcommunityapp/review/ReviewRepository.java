package com.swimmingcommunityapp.review;

import com.swimmingcommunityapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByUserId(Long id, Pageable pageable);
}
