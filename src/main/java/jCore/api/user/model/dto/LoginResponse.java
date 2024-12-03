package jCore.api.user.model.dto;

import jCore.api.auth.model.entity.Auth;
import jCore.api.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    public LoginResponse(User userEntity) {
        this.userId = userEntity.getUserId();
        this.userNm = userEntity.getUserInfo().getUserNm();
        this.roles = userEntity.getRoles();
    }
    private String userId;
    private String userNm;
    @Builder.Default
    private Set<Auth> roles = new HashSet<Auth>();
    private String token;
}