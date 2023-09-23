package com.swimmingcommunityapp.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByUserId(Long id, Pageable pageable);
    //Page<Review> findBySwimmingPoolUniqueNumber(Long uniqueNumber,Pageable pageable);
    Page<Review> findBySwimmingPoolUniqueNumber(Long uniqueNumber,Pageable pageable);

}
