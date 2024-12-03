package jCore.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"insertDt", "insertUserCd", "updateDt", "updateUserCd",
        "deleteYn", "deleteDt", "deleteUserCd", "deleteRemark"})
public abstract class BaseEntity {
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false, updatable = false, name = "INSERT_DT")
    private LocalDateTime insertDt;

    @CreatedBy
    @Column(name = "INSERT_USER_CD")
    private Long insertUserCd;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "UPDATE_DT")
    private LocalDateTime updateDt;

    @LastModifiedBy
    @Column(name = "UPDATE_USER_CD")
    private Long updateUserCd;

    @Column(name = "DELETE_YN")
    private String deleteYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "DELETE_DT")
    private LocalDateTime deleteDt;

    @Column(name = "DELETE_USER_CD")
    private Long deleteUserCd;

    @Column(name = "DELETE_REMARK")
    private String deleteRemark;
}