package jCore.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jCore.common.dto.Response;
import jCore.domain.board.model.dto.request.SaveBoardCommentRequest;
import jCore.domain.board.model.dto.request.SaveBoardPostRequest;
import jCore.domain.board.model.dto.request.SaveBoardRequest;
import jCore.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jCore.common.CommonUtils.okResponsePackaging;

@Slf4j
@Tag(name = "BoardController", description = "게시판 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    @Operation(summary="게시판 저장", description="게시판 저장 API")
    public ResponseEntity<Response> saveBoard(@RequestBody SaveBoardRequest saveBoardRequest) throws Exception {
        return okResponsePackaging(boardService.saveBoard(saveBoardRequest));
    }
    @PostMapping("/post")
    @Operation(summary="게시물 저장", description="게시물 저장 API")
    public ResponseEntity<Response> saveBoardPost(@RequestBody SaveBoardPostRequest saveBoardPostRequest) throws Exception {
        return okResponsePackaging(boardService.saveBoardPost(saveBoardPostRequest));
    }
    @PostMapping("/comment")
    @Operation(summary="게시물 저장", description="댓물 저장 API")
    public ResponseEntity<Response> saveBoardComment(@RequestBody SaveBoardCommentRequest saveBoardCommentRequest) throws Exception {
        return okResponsePackaging(boardService.saveBoardComment(saveBoardCommentRequest));
    }
}