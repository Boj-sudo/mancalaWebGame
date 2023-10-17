package com.game.assessment.tsietsimaboa.service;

import java.util.List;

import com.game.assessment.tsietsimaboa.model.Board;
import com.game.assessment.tsietsimaboa.model.Game;
import com.game.assessment.tsietsimaboa.model.Pit;
import com.game.assessment.tsietsimaboa.model.Player;

public interface GameBoardService {
	
	Player savePlayer(Player player);
	
	Game initializeGame(String player1_name, String player2_name);
	
	void makeMove(Board board, Long pit_id);
	
	Game endGame(Long game_id);
	
	List<Pit> getPitsById1(Long playerId);
	
	List<Pit> getPitsById2(Long playerId);
	
	List<Pit> getBigPitById1(Long player_id);
	
	List<Pit> getBigPitById2(Long player_id);
	
	Board getCurrentGameState(Long board_id);
	
	Pit getPitById(Long pit_id);
}