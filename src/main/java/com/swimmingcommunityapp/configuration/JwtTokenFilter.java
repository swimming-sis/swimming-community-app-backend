package com.swimmingcommunityapp.configuration;


import com.swimmingcommunityapp.User.entity.User;
import com.swimmingcommunityapp.User.service.UserService;
import com.swimmingcommunityapp.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //개찰구 역할
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //언제 막아야하는지
        //1.토큰이 없을 경우(null) - bearer
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 분리
        String token;
        try {
            token = authorizationHeader.split(" ")[1];
        }catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }

        //2.만료된 토큰을 가져올때 - 토큰 만료됬는지 확인
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }
        //3.토큰이 유효하지 않을때
        // Token- Claim에서 UserName꺼내기
        String userName = JwtTokenUtil.getUserName(token, secretKey);

        // username으로 User 찾아오기
        User user = userService.getUserByUserName(userName);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), null,List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 권한 부여
        filterChain.doFilter(request, response);
    }
}
