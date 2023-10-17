package com.game.assessment.tsietsimaboa;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.game.assessment.tsietsimaboa.model.Pit;
import com.game.assessment.tsietsimaboa.model.Board;
import com.game.assessment.tsietsimaboa.repository.PitRepository;
import com.game.assessment.tsietsimaboa.service.GameBoardService;

public class GameTest {

	@Mock
	private PitRepository pit_repository;
	
	@InjectMocks
	private GameBoardService game_service;
	
	// Initializez Mockito annotations and should be executed before any test
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	// Initialize game
	@Test
	public void testInitializeGame() {
		// Mock the behavior for PitRepository.save() to return a pit
		when(pit_repository.save(any(Pit.class))).thenReturn(new Pit());
		
		// Call the method
		assertDoesNotThrow(() -> game_service.initializeGame("Tsietsi", "Maboa"));
		
		// verify that PitRepository.save() was called
		verify(pit_repository).save(any(Pit.class));
	}
	
	// Start moving pits
	@Test
	public void testMakeMove() {
		Board board = new Board();
		
		// Mock a Pit instances
		Pit mock_pit = new Pit();
		mock_pit.setBoard(board);
		mock_pit.setIndex(0);
		mock_pit.setStones(6);
		
		// Mock the behavior of the PitRepository when getPitByBoardIdAndIndex is called
		when(pit_repository.findByBoardIdAndIndex(1L, 0)).thenReturn(mock_pit);
		
		// Call the makeMove method
		game_service.makeMove(board, 1L);
		
		// Verify that the stones in the pit are set to 0 after the move
		assertEquals(0, mock_pit.getStones());
	}
	
	// End the game
	@Test
	public void testEndGame() {
		// Call the method
		assertDoesNotThrow(() -> game_service.endGame(1L));
	}
}
