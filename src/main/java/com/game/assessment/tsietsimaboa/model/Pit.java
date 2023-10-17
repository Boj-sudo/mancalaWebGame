package com.game.assessment.tsietsimaboa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pit_id;
	
	private int index;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player1_id", referencedColumnName = "player_id")
	private Player player_1;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "player2_id", referencedColumnName = "player_id")
	private Player player_2;
	
	private PlayerEnum belongsTo;

	private int stones;
	
	private boolean isMancala;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	private Board board;

	public Pit() {

	}

	public Pit(Player player_1, Player player_2, int stones, int index, Board board, boolean isMancala) {
		super();
		this.player_1 = player_1;
		this.player_2 = player_2;
		this.stones = stones;
		this.board = board;
		this.isMancala = isMancala;
		this.index = index;
	}

	public Long getId() {
		return pit_id;
	}

	public void setId(Long id) {
		this.pit_id = id;
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public Player getPlayer1() {
		return player_1;
	}

	public Player getPlayer2() {
		return player_2;
	}
	
	public boolean isMancala() {
		return isMancala;
	}

	public void setPlayer1(Player player_1) {
		this.player_1 = player_1;
	}

	public void setPlayer2(Player player_2) {
		this.player_2 = player_2;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setMancala(boolean mancala) {
		this.isMancala = mancala;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isEmpty() {
		return stones == 0;
	}
	
	public void setBelongsTo(PlayerEnum belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public PlayerEnum getBelongsTo() {
		return belongsTo;
	}
}