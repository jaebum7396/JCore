package jCore.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, name = "INSERT_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime insertDt;

    @CreatedBy
    @JsonIgnore
    @Column(name = "INSERT_USER_CD")
    private Long insertUserCd;

    @LastModifiedDate
    @Column(name = "UPDATE_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDt;

    @LastModifiedBy
    @Column(name = "UPDATE_USER_CD")
    private Long updateUserCd;

    @Column(name = "DELETE_YN")
    private String deleteYn;

    @Column(name = "DELETE_DT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deleteDt;

    @Column(name = "DELETE_USER_CD")
    private Long deleteUserCd;

    @Column(name = "DELETE_REMARK")
    private String deleteRemark;
}