package jCore.domain.user.model.entity;

import jCore.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "USER_PROFILE_IMAGE")
public class UserProfileImage extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "USER_PROFILE_IMAGE_CD")
    private String userProfileImageCd;

    @Column(name = "USER_CD")
    private String userCd;

    @Column(name = "PROFILE_IMG_URL", nullable = true)
    private String profileImgUrl;

    @Column(name = "MAIN_YN", nullable = true)
    private String mainYn;

    @Column(name = "DEFAULT_YN", nullable = true)
    private String defaultYn;
}