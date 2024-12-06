package jCore.domain.user.model.entity;

import jCore.common.entity.BaseEntity;
import jCore.domain.board.model.entity.BoardPost;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Column(name = "PROFILE_IMG_URL", nullable = true)
    private String profileImgUrl;

    @Column(name = "MAIN_YN", nullable = true)
    private String mainYn;

    @Column(name = "DEFAULT_YN", nullable = true)
    private String defaultYn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_INFO_CD")
    private UserInfo userInfo;
}