package jcore.domain.menu.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jcore.common.entity.BaseEntity;
import jcore.domain.board.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@Entity(name = "MENU")
@Schema(description = "MENU 엔티티")
public class Menu extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "MENU_CD")
    @Schema(description = "MENU 코드", example = "")
    private String menuCd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_MENU_CD")
    private Menu parentMenu;

    @Column(name = "MENU_LEVEL", nullable = false)
    @Schema(description = "MENU LEVEL", example = "1")
    private Integer menuLevel;

    @Column(name = "MENU_NM", nullable = false)
    @Schema(description = "MENU 이름", example = "testplus")
    private String menuNm;

    @Column(name = "MENU_TYPE")
    @Schema(description = "MENU 타입", example = "NOTICE")
    private String menuType;

    @Column(name = "MENU_URL")
    @Schema(description = "MENU URL", example = "/menu")
    private String menuUrl;

    @Column(name = "VIEW_YN")
    @Schema(description = "VIEW 여부", example = "Y")
    private String viewYn;
}