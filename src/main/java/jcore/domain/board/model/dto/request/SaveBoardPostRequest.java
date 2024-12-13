package jcore.domain.board.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jcore.domain.board.model.entity.BoardPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "게시물 등록 요청")
public class SaveBoardPostRequest {
    @Schema(description = "게시물 코드", example = "", required = false)
    private String boardPostCd;

    @Schema(description = "도메인 코드", example = "JCORE", required = true)
    private String domainCd;

    @Schema(description = "게시판 코드", example = "", required = true)
    private String boardCd;

    @Schema(description = "게시물 타입", example = "testplus", required = true)
    private String boardPostType;

    @Schema(description = "게시물 제목", example = "testplus", required = true)
    private String boardPostTitle;

    @Schema(description = "게시물 내용", example = "testplus", required = true)
    private String boardPostContents;

    @Schema(description = "게시물 삭제", example = "N", required = false)
    private String deleteYn;

    public BoardPost toEntity() {
        return BoardPost.builder()
                .boardPostsCd(boardPostCd)
                .domainCd(domainCd)
                .boardPostType(boardPostType)
                .boardPostTitle(boardPostTitle)
                .boardPostContents(boardPostContents)
                .deleteYn(deleteYn != null && deleteYn.equals("Y") ? "Y" : "N")
                .build();
    }
}