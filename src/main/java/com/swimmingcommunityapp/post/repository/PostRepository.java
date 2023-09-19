package com.swimmingcommunityapp.post.repository;

import com.swimmingcommunityapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long id);
    Page<Post> findByCategoryName(String catergoryName,Pageable pageable);

    Page<Post> findByUserId(Long id, Pageable pageable);

    Page<Post> findByTitleContaining(String keyword,Pageable pageable);
}
