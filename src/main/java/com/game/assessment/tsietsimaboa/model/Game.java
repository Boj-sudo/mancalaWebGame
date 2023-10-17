package com.game.assessment.tsietsimaboa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long game_id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	private Board board;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player1_id", referencedColumnName = "player_id")
	private Player player1;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player2_id", referencedColumnName = "player_id")
	private Player player2;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "winner_id")
	private Player winner;
	
	private GameStatus game_status;
	
	public Game() {
		
	}
	
	public Game(Board board, Player player1, Player player2, Player winner) {
		super();
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
		this.winner = winner;
	}
	
	// Getters and Setters
	public Long getId() {
		return game_id;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public GameStatus getGameStatus() {
		return game_status;
	}
	
	public void setId(Long id) {
		this.game_id = id;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	public void setGameStatus(GameStatus game_status) {
		this.game_status = game_status;
	}
}
