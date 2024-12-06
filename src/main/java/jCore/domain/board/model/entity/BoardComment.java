package jCore.domain.board.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jCore.common.entity.BaseEntity;
import jCore.domain.user.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@Entity(name = "BOARD_COMMENTS")
@Schema(description = "댓글 엔티티")
public class BoardComment extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "BOARD_COMMENT_CD")
    @Schema(description = "댓글 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String boardCommentCd;

    @Column(name = "BOARD_COMMENT_CONTENTS", nullable = false)
    @Schema(description = "댓글 내용", example = "")
    private String boardCommentContents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_BOARD_COMMENT_CD")
    private BoardComment parentBoardComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOARD_POST_CD")
    private BoardPost boardPost;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_CD")
    private UserInfo writeUserInfo;
}