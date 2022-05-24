package ro.unibuc.slots.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.slots.model.Game;
import ro.unibuc.slots.model.Turn;
import ro.unibuc.slots.model.WinningCombination;
import ro.unibuc.slots.service.GameService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/game")
public class GameController {
	final private GameService gameService;

	public GameController(final GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Game> getAllGames() {
		return gameService.getAllGames();
	}

	@GetMapping("/find-by-id/{id}")
	public Game findGame(@PathVariable("id") final String id) {
		return gameService.findGame(id);
	}

	@PostMapping(value = "/create")
	public Game addNewGame(@RequestBody final Game game) {
		return gameService.addNewGame(game);
	}

	@GetMapping("/winning-combinations")
	public List<WinningCombination> getWinningCombinations(){
		return gameService.getWinningCombinations();
	}

	@PostMapping("/add-turn/{id}")
	public boolean addGameTurn(@PathVariable("id") final String id, @RequestBody final Turn turn) {
		return gameService.addGameTurn(id, turn);
	}
}
