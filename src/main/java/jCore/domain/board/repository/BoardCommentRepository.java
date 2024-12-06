package jCore.domain.board.repository;

import jCore.domain.board.model.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, String>, QuerydslPredicateExecutor<BoardComment>, BoardCommentRepositoryQ {
}