package com.swimmingcommunityapp.configuration.Token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token,String> {
    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByUserName(String userName);
}
