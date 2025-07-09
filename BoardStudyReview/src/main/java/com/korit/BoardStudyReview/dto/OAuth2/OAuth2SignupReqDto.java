package com.korit.BoardStudyReview.dto.OAuth2;

import com.korit.BoardStudyReview.entity.OAuth2User;
import com.korit.BoardStudyReview.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class OAuth2SignupReqDto {
    private String username;
    private String password;
    private String email;
    private String provider;
    private String providerUserId;

    // 일반 Signin 을 위한 User entity
    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build();
    }

    // OAuth2 로그인을 위한 OAuth2User
    public OAuth2User toOAuth2User(Integer userId) {
        return OAuth2User.builder()
                .userId(userId)
                .provider(provider)
                .providerUserId(providerUserId)
                .build();
    }
}
