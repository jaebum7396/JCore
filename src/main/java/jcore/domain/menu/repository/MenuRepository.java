package jcore.domain.menu.repository;

import jcore.domain.board.model.entity.Board;
import jcore.domain.board.repository.BoardRepositoryQ;
import jcore.domain.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, QuerydslPredicateExecutor<Menu>, BoardRepositoryQ {
    List<Menu> findByMenuLevel(int menuLevel);
}