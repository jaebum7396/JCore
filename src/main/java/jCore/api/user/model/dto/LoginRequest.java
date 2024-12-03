package jCore.api.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "로그인 요청")
public class LoginRequest {
    @Schema(description = "도메인 코드", example = "JCORE", required = true)
    private String domainCd;

    @Schema(description = "사용자 아이디", example = "testplus", required = true)
    private String userId;

    @Schema(description = "비밀번호", example = "testplus1234", required = true)
    private String userPw;
}