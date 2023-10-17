package com.game.assessment.tsietsimaboa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.game.assessment.tsietsimaboa.model.Game;
import com.game.assessment.tsietsimaboa.model.Board;
import com.game.assessment.tsietsimaboa.model.Pit;
import com.game.assessment.tsietsimaboa.service.GameBoardService;

@Controller
@RequestMapping("/game")
public class GameBoardController {

	@Autowired
	private GameBoardService game_service;

	@GetMapping("/initialize")
	public String initializeForm() {
			
		return "index";
	}

	@PostMapping("/initialize")
	public String initializeGame(@RequestParam("player1Name") String player1Name, @RequestParam("player2Name") String player2Name, Model model) {
		// Initialize the game with player names
		Game game = game_service.initializeGame(player1Name, player2Name);
		
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		List<Pit> player1Pits = game_service.getPitsById1(player1Id);
		List<Pit> player2Pits = game_service.getPitsById2(player2Id);

		List<Pit> player1Mancala = game_service.getBigPitById1(player1Id);
		List<Pit> player2Mancala = game_service.getBigPitById2(player2Id);
		
		// Add the models attributes
		model.addAttribute("game", game);
		model.addAttribute("player1Name", player1Name);
		model.addAttribute("player2Name", player2Name);
		model.addAttribute("player1Pits", player1Pits);
		model.addAttribute("player2Pits", player2Pits);
		model.addAttribute("player1Mancala", player1Mancala);
		model.addAttribute("player2Mancala", player2Mancala);

		return "index";
	}

	@PostMapping("/makeMove")
	public String makeMove(@RequestParam("pitId") Long pitId) {
		Long boardId = 1L;
		
		Board current_game = game_service.getCurrentGameState(boardId);
		
		game_service.makeMove(current_game, pitId);

		return "redirect:/game/initialize";
	}

	@PostMapping("/{gameId}")
	public String endGame(@PathVariable Long gameId) {
		game_service.endGame(gameId);

		return "redirect:/game/initialize";
	}
}
