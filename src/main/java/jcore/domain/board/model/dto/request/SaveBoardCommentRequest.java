package jcore.domain.board.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jcore.domain.board.model.entity.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "게시물 등록 요청")
public class SaveBoardCommentRequest {
    @Schema(description = "댓글 코드", example = "", required = false)
    private String boardCommentCd;

    @Schema(description = "도메인 코드", example = "JCORE", required = true)
    private String domainCd;

    @Schema(description = "게시물 코드", example = "testplus", required = true)
    private String boardPostsCd;

    @Schema(description = "부모 댓글 코드", example = "testplus", required = true)
    private String parentBoardCommentsCd;

    @Schema(description = "댓글 내용", example = "testplus", required = true)
    private String boardCommentContents;

    @Schema(description = "댓글 삭제", example = "N", required = false)
    private String deleteYn;

    public BoardComment toEntity() {
        return BoardComment.builder()
                .boardCommentCd(boardCommentCd)
                .domainCd(domainCd)
                .boardCommentContents(boardCommentContents)
                .deleteYn(deleteYn != null && deleteYn.equals("Y") ? "Y" : "N")
                .build();
    }
}