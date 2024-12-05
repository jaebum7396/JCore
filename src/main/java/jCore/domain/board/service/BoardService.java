package jCore.domain.board.service;

import jCore.common.CommonUtils;
import jCore.domain.board.model.dto.SaveBoardRequest;
import jCore.domain.board.model.entity.Board;
import jCore.domain.board.repository.BoardRepository;
import jCore.domain.user.model.entity.User;
import jCore.domain.user.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final CommonUtils commonUtils;
    private final BoardRepository boardRepository;
    public Map<String, Object> saveBoard(SaveBoardRequest saveBoardRequest) throws Exception {
        log.info("BoardService.saveBoard.params : " + saveBoardRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Board board = saveBoardRequest.toEntity();
        boardRepository.save(board);
        return resultMap;
    }
}