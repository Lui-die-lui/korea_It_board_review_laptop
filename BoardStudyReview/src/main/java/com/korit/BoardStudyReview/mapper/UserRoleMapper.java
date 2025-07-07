package com.korit.BoardStudyReview.mapper;

import com.korit.BoardStudyReview.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int addUserRole(UserRole userRole);
}
