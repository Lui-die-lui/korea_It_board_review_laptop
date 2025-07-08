package com.korit.BoardStudyReview.dto.OAuth2;

import com.korit.BoardStudyReview.entity.OAuth2User;
import com.korit.BoardStudyReview.entity.User;
import lombok.Data;

@Data
public class OAuth2MergeReqDto {
    private String username;
    private String password;
    private String provider;
    private String providerUserId;

    // OAuth2User 엔티티로 변환
    public OAuth2User toOAuth2User(Integer userId) {
        return OAuth2User.builder()
                .userId(userId)
                .provider(provider)
                .providerUserId(providerUserId)
                .build();
    }
}
