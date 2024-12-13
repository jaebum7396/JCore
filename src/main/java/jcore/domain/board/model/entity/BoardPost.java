package jcore.domain.board.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jcore.common.entity.BaseEntity;
import jcore.domain.user.model.entity.UserInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@Entity(name = "BOARD_POST")
@Schema(description = "게시물 엔티티")
public class BoardPost extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "BOARD_POST_CD")
    @Schema(description = "게시물 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String boardPostsCd;

    @Column(name = "BOARD_POST_TYPE")
    @Schema(description = "게시물 타입", example = "NOTICE")
    private String boardPostType;

    @Column(name = "BOARD_POST_TITLE", nullable = false)
    @Schema(description = "게시물 제목", example = "")
    private String boardPostTitle;

    @Column(name = "BOARD_POST_CONTENTS", nullable = false)
    @Schema(description = "게시물 내용", example = "")
    private String boardPostContents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    @JoinColumn(name = "BOARD_COMMENTS_CD")
    private List<BoardPost> boardComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOARD_CD")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_INFO_CD")
    private UserInfo writeUserInfo;
}