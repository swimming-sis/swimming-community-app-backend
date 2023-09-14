package com.swimmingcommunityapp.user.repository;

import com.swimmingcommunityapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickName(String nickname);

    Optional<User> findByUserName(String userName);
    Optional<User> findByPhoneNumber(String phoneNumber);

}
