package jcore.domain.board.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jcore.domain.board.model.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "게시판 생성 요청")
public class SaveBoardRequest {
    @Schema(description = "게시판 코드", example = "", required = false)
    private String boardCd;

    @Schema(description = "도메인 코드", example = "JCORE", required = true)
    private String domainCd;

    @Schema(description = "게시판 이름", example = "testplus", required = true)
    private String boardNm;

    @Schema(description = "게시판 타입", example = "NOTICE", required = true)
    private String boardType;

    @Schema(description = "게시판 삭제", example = "N", required = false)
    private String deleteYn;

    public Board toEntity() {
        return Board.builder()
                .boardCd(boardCd)
                .domainCd(domainCd)
                .boardNm(boardNm)
                .boardType(boardType)
                .deleteYn(deleteYn != null && deleteYn.equals("Y") ? "Y" : "N")
                .build();
    }
}