package com.korit.BoardStudyReview.controller;

import com.korit.BoardStudyReview.dto.OAuth2.OAuth2MergeReqDto;
import com.korit.BoardStudyReview.dto.OAuth2.OAuth2SignupReqDto;
import com.korit.BoardStudyReview.service.OAuth2AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Autowired
    private OAuth2AuthService oAuth2AuthService;

    @PostMapping("/merge")
    public ResponseEntity<?> mergeAccount(@RequestBody OAuth2MergeReqDto oAuth2MergeReqDto) {
        return ResponseEntity.ok(oAuth2AuthService.mergeAccount(oAuth2MergeReqDto));
        // oAuth2AuthService의 mergeAccount (if / try-catch 구조 내에 받은 oAuth2MergeReqDto를 넣음)
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody OAuth2SignupReqDto oAuth2SignupReqDto) {
        return ResponseEntity.ok(oAuth2AuthService.signup(oAuth2SignupReqDto));
    }
}
