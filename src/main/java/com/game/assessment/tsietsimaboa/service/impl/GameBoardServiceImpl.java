package com.game.assessment.tsietsimaboa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.assessment.tsietsimaboa.model.Game;
import com.game.assessment.tsietsimaboa.model.GameStatus;
import com.game.assessment.tsietsimaboa.model.Board;
import com.game.assessment.tsietsimaboa.model.Pit;
import com.game.assessment.tsietsimaboa.model.Player;
import com.game.assessment.tsietsimaboa.model.PlayerEnum;
import com.game.assessment.tsietsimaboa.repository.BoardRepository;
import com.game.assessment.tsietsimaboa.repository.GameRepository;
import com.game.assessment.tsietsimaboa.repository.PitRepository;
import com.game.assessment.tsietsimaboa.repository.PlayerRepository;
import com.game.assessment.tsietsimaboa.service.GameBoardService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GameBoardServiceImpl implements GameBoardService {

	@Autowired
	private GameRepository game_repository;

	@Autowired
	private PlayerRepository player_repository;

	@Autowired
	private BoardRepository board_repository;

	@Autowired
	private PitRepository pit_repository;

	@Override
	public Player savePlayer(Player player) {
		return player_repository.save(player);
	}
	
	@Override
	public List<Pit> getPitsById1(Long playerId) {
        return pit_repository.getPitsById1(playerId);
    }
	
	@Override
	public List<Pit> getPitsById2(Long playerId) {
        return pit_repository.getPitsById2(playerId);
    }
	
	@Override
	public List<Pit> getBigPitById1(Long playerId) {
		return pit_repository.getBigPitById1(playerId);
	}
	
	@Override
	public List<Pit> getBigPitById2(Long playerId) {
		return pit_repository.getBigPitById2(playerId);
	}

	@Override
	public Game initializeGame(String player1_name, String player2_name) {
		// Create player 1 and set the name
		Player player_1 = new Player();
		player_1.setName(player1_name);
		player_1 = savePlayer(player_1);

		// Create player 2 and set the name
		Player player_2 = new Player();
		player_2.setName(player2_name);
		player_2 = savePlayer(player_2);

		// Create a board and associate it with the players
		Board board_1 = new Board();
		Board board_2 = new Board();

		player_1.setBoard(board_1);
		player_2.setBoard(board_2);

		// Initialize the pits for player 1
		List<Pit> player1_pits = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();

			pit.setStones(6);
			pit.setPlayer1(player_1);
			pit.setBoard(board_1);
			pit.setIndex(i);
			//pit.setGame(game);
			player1_pits.add(pit);
		}
		
		int index = 6;
		// Set the Mancala pit for player 1
		Pit player1_big_pit = new Pit();
		player1_big_pit.setStones(0);
		player1_big_pit.setPlayer1(player_1);
		player1_big_pit.setMancala(true);
		player1_big_pit.setBoard(board_1);
		player1_big_pit.setIndex(index);
		
		// Initialize the pits for player 2
		List<Pit> player2_pits = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();

			pit.setStones(6);
			pit.setPlayer2(player_2);
			pit.setBoard(board_2);
			pit.setIndex(i);
			//pit.setGame(game);
			player2_pits.add(pit);
		}

		// Set the Mancala pit for player 2
		Pit player2_big_pit = new Pit();
		player2_big_pit.setStones(0);
		player2_big_pit.setPlayer2(player_2);
		player2_big_pit.setMancala(true);
		player2_big_pit.setBoard(board_2);
		player1_big_pit.setIndex(index);

		// Set the pits for player 1 and player 2
		board_1.setPits(player1_pits);
		board_1.getPits().addAll(player2_pits);
		board_1.getPits().add(player1_big_pit);
		board_1.getPits().add(player2_big_pit);

		// Save entities to the database
		player_repository.save(player_1);
		player_repository.save(player_2);

		board_repository.save(board_1);
		board_repository.save(board_2);

		pit_repository.saveAll(player1_pits);
		pit_repository.saveAll(player2_pits);

		// Create the game and set up player game relationships
		Game game = new Game();
		game.setPlayer1(player_1);
		game.setPlayer2(player_2);
		game.setBoard(board_1);
		game.setGameStatus(GameStatus.NOT_STARTED);

		// Save the game
		return game_repository.save(game);
	}
	
	@Override
	public Board getCurrentGameState(Long board_id) {
		// Retrieve the game state based on the provided game id
		return board_repository.findById(board_id).orElseThrow(() -> new EntityNotFoundException("Game not found with ID: " + board_id));
	}
	
	@Override
	public Pit getPitById(Long pit_id) {
		return pit_repository.findById(pit_id).orElseThrow(() -> new EntityNotFoundException("Pit not found with ID: " + pit_id));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void makeMove(Board board, Long pit_id) {
		// Retrieve the pit
		Pit selected_pit = getPitById(pit_id);
		
		// Check if the selected pit is valid for the current player and
		// ensure that the pit belongs to the current player and is not empty
		if(selected_pit.getPlayer1().equals(board.getCurrentPlayer()) || selected_pit.getPlayer2().equals(board.getCurrentPlayer()) && selected_pit.getStones() > 0) {
			int stonesToDistribute = selected_pit.getStones();
			selected_pit.setStones(0);
			
			// Distribute the stones to subsequent pits
			Pit current_pit = selected_pit;
			while(stonesToDistribute > 0) {
				current_pit = distributeStone(current_pit, board);
				stonesToDistribute--;
				// Capture stones 
				captureStones(board.getId(), current_pit.getIndex());
			}
			
			// Check if the last stone landed in the player's own big pit and
			// if true, then the player gets another turn
			if(current_pit.isMancala() && current_pit.getPlayer1().equals(board.getCurrentPlayer()) || current_pit.getPlayer2().equals(board.getCurrentPlayer())) {
				board.setPlayerTurn(board.getCurrentPlayer()); 	// Player gets another turn
			} else {
				// Update the game state to switch to the other player's turn
				board.switchPlayerTurn();
			}
			
			// Save the updated game state
			board_repository.save(board);
		} else {
			
		}
	}
	
	private Pit distributeStone(Pit pit, Board board) {
		int nextPit_index = (pit.getIndex() + 1) % board.getTotalPits();
		
		// Skip opponent's big pit
		if(pit.isMancala() && !board.isCurrentPlayerBigPit(nextPit_index)) {
			nextPit_index = (nextPit_index + 1) % board.getTotalPits();
		}
		
		// Increment stone count in the next pit
		Pit next_pit = board.getPits().get(nextPit_index);
		next_pit.setStones(next_pit.getStones() + 1);
		
		return next_pit;
	}

	private void captureStones(Long board_id, int pit_index) {
		// Get the pit at the specified index
		Pit pit = pit_repository.findByBoardIdAndIndex(board_id, pit_index);
		
		// Check if the pit is empty and belongs to the current player
		if(pit.isEmpty() && pit.getBelongsTo() == PlayerEnum.PLAYER_1) {
			int oppositePitIndex = 12 - pit_index - 1;
			
			//Get the opposite pit
			Pit opposite_pit = pit_repository.findByBoardIdAndIndex(board_id, pit_index);
			
			// Capture stones from the opposite pit and the current pit
			int captured_stones = opposite_pit.getStones() + pit.getStones();
			
			// Update the stones in the current pit and the opposite pit to 0
			pit.setStones(0);
			opposite_pit.setStones(0);
			
			// Update the pits in the database
			pit_repository.save(pit);
			pit_repository.save(opposite_pit);
			
			// Update the big pit (assuming the big pit index is known
			int bigPitIndex = 13;
			Pit big_pit = pit_repository.findByBoardIdAndIndex(board_id, bigPitIndex);
			big_pit.setStones(big_pit.getStones() + captured_stones);
			pit_repository.save(big_pit);
		}
	}

	private Player determineWinner(Game game) {
		Player player_1 = game.getPlayer1();
		Player player_2 = game.getPlayer2();

		int player1_total_stones = calculateTotalStones(player_1);
		int player2_total_stones = calculateTotalStones(player_2);

		if (player1_total_stones == player2_total_stones) {
			// It's a tie
			return null;
		} else if (player1_total_stones > player2_total_stones) {
			return player_1;
		} else {
			return player_2;
		}
	}

	private int calculateTotalStones(Player player) {
		int total_stones = 0;

		/**for (Integer stones : Player.getStore1()) {
			total_stones += stones;
		}*/

		return total_stones;
	}

	/**
	 * @summary This method is to move the remaining stones to the respective
	 *          player's big pit and empty the pits.
	 */
	@Override
	public Game endGame(Long game_id) {
		// Fetch the game and validate its existence
		Optional<Game> optional_game = game_repository.findById(game_id);
		if (!optional_game.isPresent()) {
			throw new EntityNotFoundException("Game not found with ID: " + game_id);
		}

		Game game = optional_game.get();

		// Determine the winner of the game
		Player winner = determineWinner(game);

		// Update game status and winner
		game.setGameStatus(GameStatus.ENDED);
		game.setWinner(winner);

		// Save the updated game state
		return game_repository.save(game);
	}
}
