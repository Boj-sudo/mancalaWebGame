package com.game.assessment.tsietsimaboa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.assessment.tsietsimaboa.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
