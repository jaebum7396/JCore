package jcore.domain.board.service;

import jcore.common.CommonUtils;
import jcore.domain.board.model.dto.request.SaveBoardCommentRequest;
import jcore.domain.board.model.dto.request.SaveBoardPostRequest;
import jcore.domain.board.model.dto.request.SaveBoardRequest;
import jcore.domain.board.model.entity.Board;
import jcore.domain.board.model.entity.BoardComment;
import jcore.domain.board.model.entity.BoardPost;
import jcore.domain.board.repository.BoardCommentRepository;
import jcore.domain.board.repository.BoardPostRepository;
import jcore.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final CommonUtils commonUtils;
    private final BoardRepository boardRepository;
    private final BoardPostRepository boardPostRepository;
    private final BoardCommentRepository boardCommentRepository;
    public Map<String, Object> saveBoard(SaveBoardRequest saveBoardRequest) throws Exception {
        log.info("BoardService.saveBoard.params : " + saveBoardRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // 1. Board 생성
        Board board = saveBoardRequest.toEntity();

        // 2. 저장
        boardRepository.save(board);

        // 3. 저장 후 결과 맵에 정보 추가
        resultMap.put("boardCd", board.getBoardCd());

        return resultMap;
    }
    public Map<String, Object> saveBoardPost(SaveBoardPostRequest saveBoardPostRequest) throws Exception {
        log.info("BoardService.saveBoardPost.params : " + saveBoardPostRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // 1. Board 조회
        Board board = boardRepository.findById(saveBoardPostRequest.getBoardCd())
                .orElseThrow(() -> new EntityNotFoundException("게시판을 찾을 수 없습니다."));

        // 2. BoardPost 생성 및 Board 매핑
        BoardPost boardPost = saveBoardPostRequest.toEntity();
        boardPost.setBoard(board);  // Board 엔티티 매핑

        // 3. 저장
        boardPostRepository.save(boardPost);

        // 4. 저장 후 결과 맵에 정보 추가
        resultMap.put("boardPostCd", boardPost.getBoardPostsCd());

        return resultMap;
    }

    public Map<String, Object> saveBoardComment(SaveBoardCommentRequest saveBoardCommentRequest) throws Exception {
        log.info("BoardService.saveBoardComment.params : " + saveBoardCommentRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // 1. BoardPost 조회
        BoardPost boardPost = boardPostRepository.findById(saveBoardCommentRequest.getBoardPostsCd())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        // 2. 부모 BoardComment가 존재하는 경우, 부모 댓글의 하위 댓글로 생성
        BoardComment parentBoardComment = null;
        if (saveBoardCommentRequest.getParentBoardCommentsCd() != null &&
                !saveBoardCommentRequest.getParentBoardCommentsCd().isBlank()) {
            // 2-1. 부모 BoardComment 조회
            parentBoardComment = boardCommentRepository.findById(saveBoardCommentRequest.getParentBoardCommentsCd())
                    .orElseThrow(() -> new EntityNotFoundException("부모 댓글을 찾을 수 없습니다."));
        }

        // 3. BoardComment 생성 및 BoardPost 매핑
        BoardComment boardComment = saveBoardCommentRequest.toEntity();
        boardComment.setBoardPost(boardPost);  // BoardPost 엔티티 매핑
        if (parentBoardComment != null) {
            boardComment.setParentBoardComment(parentBoardComment);  // 부모 BoardComment 엔티티 매핑
        }

        // 4. 저장
        boardCommentRepository.save(boardComment);

        // 5. 저장 후 결과 맵에 정보 추가
        resultMap.put("boardCommentCd", boardComment.getBoardCommentCd());

        return resultMap;
    }
}