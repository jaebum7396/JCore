package jCore.api.user.model.dto;

import jCore.api.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final User userEntity;
    public CustomUserDetails(User userEntity) {
        this.userEntity = userEntity;
    }
    public final User getMember() {
        return userEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRoles().stream().map(o -> new SimpleGrantedAuthority(o.getAuthType())).collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return userEntity.getUserPw();
    }
    @Override
    public String getUsername() {
        return userEntity.getUserId();
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
        return true;
    }
}