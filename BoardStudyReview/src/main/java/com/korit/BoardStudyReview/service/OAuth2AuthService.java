package com.korit.BoardStudyReview.service;

import com.korit.BoardStudyReview.dto.ApiRespDto;
import com.korit.BoardStudyReview.dto.OAuth2.OAuth2MergeReqDto;
import com.korit.BoardStudyReview.entity.OAuth2User;
import com.korit.BoardStudyReview.entity.User;
import com.korit.BoardStudyReview.repositroy.OAuth2UserRepository;
import com.korit.BoardStudyReview.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OAuth2AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> mergeAccount(OAuth2MergeReqDto oAuth2MergeReqDto) {
        // 해당 id의 정보가 있는지 확인
        Optional<User> optionalUser = userRepository.getUserByUsername(oAuth2MergeReqDto.getUsername());
        // userRepository의 getUserByUsername에 oAuth2MergeReqDto가 가지고있는 Username을 줌
        if (optionalUser.isEmpty()) { // 회원 정보가 없을때
            return new ApiRespDto<>("failed", "사용자 정보를 확인하세요",null);
        }

        User user = optionalUser.get();

       // 이미 소셜 로그인 연동이 되어있는지
        Optional<OAuth2User> optionalOAuth2User = oAuth2UserRepository
                .getOAuth2UserByProviderAndProviderUserId(oAuth2MergeReqDto.getProvider(), oAuth2MergeReqDto.getProviderUserId());
        // 전달받은 provider + providerUserId로 이미 등록된 소셜 계정이 있는지 확인
        if (optionalOAuth2User.isPresent()) { // 계정이 존재 한다면,
            return new ApiRespDto<>("failed", "이 계정은 이미 소셜 계정과 연동되어 있습니다.",null);
        }

        if (!bCryptPasswordEncoder.matches(oAuth2MergeReqDto.getPassword(), user.getPassword())) { // 평문 password / 암호화된 기존 password 비교
            return new ApiRespDto<>("failed","사용자 정보를 확인하세요.",null);
        }

        try { // 여기서 예외가 발생하면 rollback
            int result = oAuth2UserRepository.addOAuth2User(oAuth2MergeReqDto.toOAuth2User(user.getUserId()));
            if (result != 1) {
                throw new RuntimeException("OAuth2 사용자 정보 연동에 실패했습니다.");
            }
            // 통과하면 성공
            return new ApiRespDto<>("success","성공적으로 계정 연동이 되었습니다.",null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "계정 연동 중 오류가 발생했습니다. : " + e.getMessage(), null);
        }


    }
}
