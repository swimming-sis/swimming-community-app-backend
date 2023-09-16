package com.swimmingcommunityapp.swimmingPool;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SwimmingPoolRepository extends JpaRepository<SwimmingPool,Long> {
    Optional<SwimmingPool> findByUniqueNumber(Long uniqueNumber);
}
