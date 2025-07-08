package com.korit.BoardStudyReview.mapper;

import com.korit.BoardStudyReview.entity.OAuth2User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface OAuth2UserMapper {
    // provider, provideruserid 가지고 있는지 확인해야함
    Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId);
    int addOAuth2User(OAuth2User oAuth2User);
}
