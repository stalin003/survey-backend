package com.example.survey.util;

import com.example.survey.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;
    private boolean disabled;

    public MyUserDetails(Users users) {
        this.username = users.getEmail();
        this.password = users.getPassword();
        this.disabled = users.isActive();


        this.grantedAuthorityList = users.getRoles().stream()
                .map(roles -> {
                    return new SimpleGrantedAuthority(roles.getRoles().toString());
                }).collect(Collectors.toList());

    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return disabled;
    }
}
