package com.korit.BoardStudyReview.sequrity.filter;


import com.korit.BoardStudyReview.entity.User;
import com.korit.BoardStudyReview.repositroy.UserRepository;
import com.korit.BoardStudyReview.sequrity.jwt.JwtUtils;
import com.korit.BoardStudyReview.sequrity.model.PrincipalUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter implements Filter { // jakarta 필터

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Override // 메서드 구현 doFilter 불러오기
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        List<String> methods = List.of("POST", "GET", "PUT", "PATCH", "DELETE");
        if (methods.contains(request.getMethod())) { // methods 가 포함하고있지 않으면
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (jwtUtils.isBearer(authorization)) { // 만약 http header 값으로 authorization 문자열이 들어왔는데, Bearer 토큰 이면
            String accessToken = jwtUtils.removeBearer(authorization);
            // 앞에 bearer접두사 제거하고 토큰만 들고오게끔 만듦
            try {
                Claims claims = jwtUtils.getClaims(accessToken);
                String id = claims.getId(); // 넣어놓은 id를
                Integer userId = Integer.parseInt(id); // Integer 형태로 바꿔줌
                Optional<User> optionalUser = userRepository.getUserByUserId(userId); // userid로 찾으려고
                optionalUser.ifPresentOrElse((user) -> {
                    PrincipalUser principalUser = PrincipalUser.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .email(user.getEmail())
                            .userRoles(user.getUserRoles())
                            .build();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser,"",principalUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }, () -> {
                    throw new AuthenticationServiceException("인증 실패");
                });
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse); // 다음 필터로 넘어가라
    }
}
