package com.game.assessment.tsietsimaboa.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long player_id;
	
	private String player_name;
	
	/**
	 * Player has a one-to-many relationship with the Pit as each player has multuple pits in their board
	 */
	@OneToMany(mappedBy = "player_1")
	private List<Pit> pits_1;
	
	@OneToMany(mappedBy = "player_2")
	private List<Pit> pits_2;
	
	@OneToOne(mappedBy = "player_1")
	private Pit store1;
	
	@OneToOne(mappedBy = "player_2")
	private Pit store2;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	private Board board;
	
	@OneToOne(mappedBy = "player1")
	private Game game1;
	
	@OneToOne(mappedBy = "player2")
	private Game game2;
	
	@OneToMany(mappedBy = "winner")
	private List<Game> winners;
	
	public Player() {
		
	}
	
	public Player(String name, List<Pit> pits_1, List<Pit> pits_2, Pit store1, Pit store2, Board board, Game game1, Game game2, List<Game> winners) {
		super();
		this.pits_1 = pits_1;
		this.pits_2 = pits_2;
		this.store1 = store1;
		this.store2 = store2;
		this.board = board;
		this.game1 = game1;
		this.game2 = game2;
		this.winners = winners;
	}
	
	public Long getId() {
		return player_id;
	}
	
	public void setId(Long id) {
		this.player_id = id;
	}
	
	public List<Pit> getPits1() {
		return pits_1;
	}
	
	public List<Pit> getPits2() {
		return pits_2;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Game getGame1() {
		return game1;
	}
	
	public Game getGame2() {
		return game2;
	}
	
	public String getName() {
		return player_name;
	}
	
	public List<Game> getWinner() {
		return winners;
	}
	
	public void setName(String name) {
		this.player_name = name;
	}
	
	public void setWinner(List<Game> winner) {
		this.winners = winner;
	}
	
	public void setPits1(List<Pit> pits) {
		this.pits_1 = pits;
	}
	
	public void setPits2(List<Pit> pits) {
		this.pits_2 = pits;
	}
	
	public Pit getStore1() {
		return store1;
	}
	
	public void setStore1(Pit store1) {
		this.store1 = store1;
	}
	
	public Pit getStore2() {
		return store2;
	}
	
	public void setStore2(Pit store2) {
		this.store2 = store2;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setGame1(Game game) {
		this.game1 = game;
	}
	
	public void setGame2(Game game) {
		this.game2 = game;
	}
}