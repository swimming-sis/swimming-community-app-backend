package com.swimmingcommunityapp.User.repository;

import com.swimmingcommunityapp.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickName(String nickname);

    Optional<User> findByUserName(String userName);
}
