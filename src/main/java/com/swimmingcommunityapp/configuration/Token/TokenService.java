package com.swimmingcommunityapp.configuration.Token;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    //token 저장
    @Transactional
    public void saveToken(String userName,String refreshToken, String accessToken){
        tokenRepository.save(new Token(userName,refreshToken,accessToken));
    }
    @Transactional
    public void removeRefreshToken(String accessToken) {
        tokenRepository.findByAccessToken(accessToken)
                .ifPresent(refreshToken -> tokenRepository.delete(refreshToken));

    }

}
