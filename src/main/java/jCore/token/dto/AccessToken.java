package jCore.token.dto;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash(value = "accessToken", timeToLive = 60)
public class AccessToken {

    @Id
    private String accessToken;
    private Long memberId;

    public AccessToken(final String accessToken, final Long memberId) {
        this.accessToken = accessToken;
        this.memberId = memberId;
    }

    public String getRefreshToken() {
        return accessToken;
    }

    public Long getMemberId() {
        return memberId;
    }
}