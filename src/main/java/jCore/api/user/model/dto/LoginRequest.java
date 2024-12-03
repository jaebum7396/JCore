package jCore.api.user.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @ApiModelProperty(value="domainCd", example="1", required=true)
    @ColumnDefault("1")
    private String domainCd;
    @ApiModelProperty(value="userId", example="TEST_PLUS", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="TEST_PLUS1234", required=true)
    private String userPw;
}