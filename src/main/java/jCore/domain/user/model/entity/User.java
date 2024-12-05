package jCore.domain.user.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jCore.domain.auth.model.entity.Auth;
import jCore.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@Entity(name = "USER")
@Schema(description = "사용자 엔티티")
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "USER_CD")
    @Schema(description = "사용자 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String userCd;

    @Column(name = "DOMAIN_CD")
    @ColumnDefault("1")
    @Schema(description = "도메인 코드", example = "JCORE")
    private String domainCd;

    @Column(name = "USER_ID", nullable = false)
    @Schema(description = "사용자 아이디", example = "testplus")
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USER_PW", nullable = false)
    @Schema(description = "사용자 비밀번호", example = "testplus1234")
    private String userPw;

    @Column(name = "USER_TYPE")
    @Schema(description = "사용자 유형", example = "ADMIN")
    private String userType;

    @Column(name = "USER_STATUS", nullable = true)
    @ColumnDefault("1")
    @Schema(description = "사용자 상태", example = "1")
    private String userStatus;

    @Column(name = "USER_NM")
    @Schema(description = "사용자 이름", example = "테스트플러스")
    private String userNm;

    @Column(name = "USER_PHONE_NO")
    @Schema(description = "사용자 전화번호", example = "010-0000-0366")
    private String userPhoneNo;

    @Column(name = "USER_BIRTH")
    @Schema(description = "사용자 생년월일", example = "1990-01-01")
    private String userBirth;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_CD")
    @Schema(description = "사용자 상세 정보")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @Schema(description = "사용자 권한 목록")
    private Set<Auth> roles = new HashSet<>();

    public void setRoles(Set<Auth> roles) {
        this.roles = new HashSet<>(roles);
        //roles.forEach(o -> o.setUser(this));
    }
}