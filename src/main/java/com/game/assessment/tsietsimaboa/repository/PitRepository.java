package com.game.assessment.tsietsimaboa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.game.assessment.tsietsimaboa.model.Pit;

@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {
	@Query(
			value = "SELECT * FROM Pit p WHERE p.player1_id = 1 AND p.is_mancala=FALSE",
			nativeQuery = true
	)
	List<Pit> getPitsById1(@Param("player1Id") Long playerId);
	
	@Query(
			value = "SELECT * FROM Pit p WHERE p.player2_id = 2 AND p.is_mancala=FALSE",
			nativeQuery = true
	)
	List<Pit> getPitsById2(@Param("player2Id") Long playerId);
	
	@Query(
			value = "SELECT * FROM Pit p WHERE p.player1_id = 1 AND p.is_mancala=TRUE",
			nativeQuery = true
	)
	List<Pit> getBigPitById1(Long playerId);
	
	@Query(
			value = "SELECT * FROM Pit p WHERE p.player2_id = 2 AND p.is_mancala=TRUE",
			nativeQuery = true
	)
	List<Pit> getBigPitById2(Long playerId);
	
	Pit findByBoardIdAndIndex(@Param("board_id") Long board_id, int pit_index);
}
