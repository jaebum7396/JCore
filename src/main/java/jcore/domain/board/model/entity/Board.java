package jcore.domain.board.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jcore.common.entity.BaseEntity;
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
@Entity(name = "BOARD")
@Schema(description = "게시판 엔티티")
public class Board extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "BOARD_CD")
    @Schema(description = "게시판 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String boardCd;

    @Column(name = "BOARD_NM", nullable = false)
    @Schema(description = "게시판 이름", example = "testplus")
    private String boardNm;

    @Column(name = "BOARD_TYPE")
    @Schema(description = "게시판 타입", example = "NOTICE")
    private String boardType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    @JoinColumn(name = "BOARD_CD")
    private List<BoardPost> boardPosts = new ArrayList<>();
}