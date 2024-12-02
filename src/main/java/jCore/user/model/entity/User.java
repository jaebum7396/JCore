package jCore.user.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "USER_CD")
    private String userCd;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) @JoinColumn(name = "USER_CD")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL) @Builder.Default
    private Set<Auth> roles = new HashSet<>();

    @Column(name = "DOMAIN_CD") @ColumnDefault("1")
    private String domainCd;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USER_PW", nullable = false)
    private String userPw;

    @Column(name = "USER_TYPE",nullable = true) @ColumnDefault("1")
    private String userType;

    @Column(name = "USER_STATUS",nullable = true) @ColumnDefault("1")
    private String userStatus;

    @Column(name = "USER_NM",nullable = false)
    private String userNm;

    @Column(name = "USER_PHONE_NUMBER",nullable = false)
    private String userPhoneNo;

    @Column(name = "USER_BIRTH",nullable = true)
    private String userBirth;

    public void setRoles(Set<Auth> roles) {
        this.roles = new HashSet<>(roles);
        //roles.forEach(o -> o.setUser(this));
    }
}