package com.korit.BoardStudyReview.sequrity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.korit.BoardStudyReview.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PrincipalUser implements UserDetails {
    private Integer userId;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList()); // 람다식으로 SimpleGrantedAuthority 객체 만듦. rolename받아옴
    }
}
