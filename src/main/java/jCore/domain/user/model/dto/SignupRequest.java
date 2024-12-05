package jCore.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jCore.domain.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "회원가입 요청")
public class SignupRequest {
    @Schema(description = "도메인 코드", example = "JCORE", required = true)
    private String domainCd;

    @Schema(description = "사용자 아이디", example = "testplus", required = true)
    private String userId;

    @Schema(description = "비밀번호", example = "testplus1234", required = true)
    private String userPw;

    @Schema(description = "사용자 이름", example = "테스트플러스", required = true)
    private String userNm;

    @Schema(description = "전화번호", example = "010-0000-0366", required = true)
    private String userPhoneNo;

    @Schema(description = "성별", example = "M,W", required = false)
    private String userGender;

    public User toEntity() {
        return User.builder()
                .domainCd(domainCd)
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}