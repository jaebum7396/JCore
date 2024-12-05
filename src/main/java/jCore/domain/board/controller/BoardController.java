package jCore.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jCore.common.CommonUtils;
import jCore.common.dto.Response;
import jCore.domain.auth.service.AuthService;
import jCore.domain.board.model.dto.SaveBoardRequest;
import jCore.domain.board.service.BoardService;
import jCore.domain.user.model.dto.LoginRequest;
import jCore.domain.user.model.dto.SignupRequest;
import jCore.domain.user.model.entity.UserInfo;
import jCore.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@Tag(name = "BoardController", description = "게시판 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final CommonUtils commonUtils;
    private final BoardService boardService;
    @PostMapping
    @Operation(summary="게시판 생성", description="게시판 생성 API")
    public ResponseEntity<Response> saveBoard(@RequestBody SaveBoardRequest saveBoardRequest) throws Exception {
        return commonUtils.okResponsePackaging(boardService.saveBoard(saveBoardRequest));
    }
}