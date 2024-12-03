package jCore.api.user.model.entity;

import jCore.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    @Id @Column(name = "USER_CD")
    private String userCd;

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

    @Column(name = "FCM_TOKEN", length = 3000, nullable = true)
    private String fcmToken;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    @JoinColumn(name = "USER_CD")
    private List<UserProfileImage> userProfileImages = new ArrayList<>();

    public void addUserProfileImage(UserProfileImage userProfileImage) {
        this.userProfileImages.add(userProfileImage);
    }

    public void setUserProfileImages(List<UserProfileImage> userProfileImages) {
        this.userProfileImages = userProfileImages;
    }
}

