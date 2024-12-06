package jCore.domain.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jCore.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "USER_INFO")
public class UserInfo extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "USER_INFO_CD")
    @Schema(description = "사용자 정보 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String userInfoCd;

    @Column(name = "USER_NM", nullable = true)
    private String userNm;

    @Column(name = "USER_PHONE_NUMBER", nullable = true)
    private String userPhoneNo;

    @Column(name = "USER_BIRTH",nullable = true)
    private String userBirth;

    @Column(name = "USER_NICK_NM",nullable = true)
    private String userNickNm;

    @Column(name = "ABOUT_ME", length = 3000, nullable = true)
    private String aboutMe;

    @Column(name = "USER_GENDER",nullable = true)
    private String userGender;

    @Column(name = "USER_CHARACTER", nullable = true)
    private String userCharacter;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_CD")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    @JoinColumn(name = "USER_PROFILE_IMAGE_CD")
    @JsonIgnore
    private List<UserProfileImage> userProfileImages = new ArrayList<>();
}

