package com.swimmingcommunityapp.log;

import com.swimmingcommunityapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByDateAndUserId (String date,Long userId, Pageable pageable);

    Page<Log> findByUserId(Long userId, Pageable pageable);
}
