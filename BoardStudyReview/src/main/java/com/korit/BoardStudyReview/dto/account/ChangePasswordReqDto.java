package com.korit.BoardStudyReview.dto.account;

import com.korit.BoardStudyReview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class ChangePasswordReqDto {
    private Integer userId;
    private String oldPassword;
    private String newPassword;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) { // 암호화 시켜야 하기 때문
        return User.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(newPassword))
                .build();
        // 나중에 반환할때 이전 패스워드는 필요없으니까
    }
}
