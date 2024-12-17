package jcore.domain.menu.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jcore.domain.menu.model.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "MENU 생성 요청")
public class SaveMenuRequest {
    @Schema(description = "MENU 코드", example = "", required = false)
    private String menuCd;

    @Schema(description = "부모 MENU 코드", example = "", required = false)
    private String parentMenuCd;

    @Schema(description = "MENU 이름", example = "testplus", required = true)
    private String menuNm;

    @Schema(description = "MENU 타입", example = "NOTICE", required = true)
    private String menuType;

    @Schema(description = "MENU 레벨", example = "0", required = true)
    private int menuLevel;

    @Schema(description = "MENU URL", example = "NOTICE", required = true)
    private String menuUrl;

    @Schema(description = "VIEW 여부", example = "Y", required = true)
    private String viewYn;

    @Schema(description = "MENU 삭제", example = "N", required = false)
    private String deleteYn;

    public Menu toEntity() {
        return Menu.builder()
                .menuCd(menuCd)
                .menuNm(menuNm)
                .menuType(menuType)
                .menuLevel(menuLevel)
                .menuUrl(menuUrl)
                .viewYn(viewYn)
                .deleteYn(deleteYn != null && deleteYn.equals("Y") ? "Y" : "N")
                .build();
    }
}