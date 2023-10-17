package com.game.assessment.tsietsimaboa.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board_id;

	@OneToMany(mappedBy = "board")
	private List<Pit> pits;

	@OneToOne(mappedBy = "board")
	private Player player_1;

	@OneToOne(mappedBy = "board")
	private Player player_2;

	@OneToOne(mappedBy = "board")
	private Game game;
	
	private int current_player;

	public Board() {

	}

	public Board(List<Pit> pits, Player player_1, Player player_2, Game game) {
		super();
		this.pits = pits;
		this.player_1 = player_1;
		this.player_2 = player_2;
		this.game = game;
	}

	// Getters and Setters
	public Long getId() {
		return board_id;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public Player getPlayer1() {
		return player_1;
	}

	public Player getPlayer2() {
		return player_2;
	}

	public Game getGame() {
		return game;
	}

	public void setId(Long id) {
		this.board_id = id;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public void setPlayer1(Player player_1) {
		this.player_1 = player_1;
	}

	public void setPlayer2(Player player_2) {
		this.player_2 = player_2;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public int getCurrentPlayer() {
		return current_player;
	}
	
	public void setCurrentPlayer(int current_player) {
		this.current_player = current_player;
	}

	// Get the total number of pits
	public int getTotalPits() {
		return pits.size();
	}

	// Check if the pit is the current player's big pit
	public boolean isCurrentPlayerBigPit(int pit_index) {
		int player1BigPitIndex = 13;
		int player2BigPitIndex = 14;

		if (current_player == 1) {
			return pit_index == player1BigPitIndex;
		} else if (current_player == 2) {
			return pit_index == player2BigPitIndex;
		}

		return false;
	}

	// Set the current player's turn
	public void setPlayerTurn(int player_number) {
		if (player_number != 1 && player_number != 2) {
			throw new IllegalArgumentException("Invalid player number. Must be 1 or 2.");
		}

		this.current_player = player_number;
	}

	// Switch to the other player's turn
	public void switchPlayerTurn() {
		this.current_player = (current_player == 1) ? 2 : 1;
	}
}
