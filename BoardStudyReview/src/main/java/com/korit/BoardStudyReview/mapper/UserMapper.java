package com.korit.BoardStudyReview.mapper;

import com.korit.BoardStudyReview.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int addUser(User user);
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);

    int updatePassword(User user);
}
