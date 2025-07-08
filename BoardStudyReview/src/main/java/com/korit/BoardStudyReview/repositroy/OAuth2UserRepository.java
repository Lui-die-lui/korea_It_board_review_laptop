package com.korit.BoardStudyReview.repositroy;

import com.korit.BoardStudyReview.entity.OAuth2User;
import com.korit.BoardStudyReview.mapper.OAuth2UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OAuth2UserRepository {

    @Autowired
    private OAuth2UserMapper oAuth2UserMapper;

    // 위 Mapper를 가져와 String provider, String providerUserId를 매개변수로 사용
    public Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId) {
        return oAuth2UserMapper.getOAuth2UserByProviderAndProviderUserId(provider, providerUserId);
    }

    // OAuth2User의 추가 및 성공여부 반환
    public int addOAuth2User(OAuth2User oAuth2User) {
        return oAuth2UserMapper.addOAuth2User(oAuth2User);
    }
}
