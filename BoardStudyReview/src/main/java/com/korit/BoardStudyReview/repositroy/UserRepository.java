package com.korit.BoardStudyReview.repositroy;

import com.korit.BoardStudyReview.entity.User;
import com.korit.BoardStudyReview.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    public Optional<User> addUser(User user) {
       try {
           userMapper.addUser(user);
       } catch (DuplicateKeyException e) { // 중복된 값이 있으면
           return Optional.empty(); // 추가 실패
       }
       return Optional.of(user);
    }

    public Optional<User> getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

}
