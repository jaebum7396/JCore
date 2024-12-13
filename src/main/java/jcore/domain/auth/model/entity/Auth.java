package jcore.domain.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jcore.domain.user.model.entity.User;
import jcore.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity(name = "AUTH")
public class Auth extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "AUTH_CD")
    private String authCd;

    @Column(name = "AUTH_TYPE")
    private String authType;

    @JoinColumn(name = "USER_CD") @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    private User userEntity;

    public void setUser(User userEntity) {
        this.userEntity = userEntity;
    }
}