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
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/oauth2/authorization/") || requestUri.startsWith("/login/oauth2/code/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        List<String> methods = List.of("POST", "GET", "PUT", "PATCH", "DELETE");
        if (!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if (jwtUtils.isBearer(authorization)) {
            String accessToken = jwtUtils.removeBearer(authorization);

            try {
                Claims claims = jwtUtils.getClaims(accessToken);
                String id = claims.getId();
                Integer userId = Integer.parseInt(id);
                Optional<User> optionalUser = userRepository.getUserByUserId(userId);
                optionalUser.ifPresentOrElse((user) -> {
                    PrincipalUser principalUser = PrincipalUser.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .email(user.getEmail())
                            .userRoles(user.getUserRoles())
                            .build();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }, () -> {
                    throw new AuthenticationServiceException("인증 실패");
                });
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}