package com.slots.app.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.slots.app.dto.TurnDto;
import com.slots.app.model.Game;
import com.slots.app.model.Turn;
import com.slots.app.model.WinningCombination;
import com.slots.app.repo.GameRepository;
import com.slots.app.service.SlotService;

@RestController
@RequestMapping(value = "/rest/game")
public class GameController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private SlotService slotService;

	public GameController() {

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Game> getAllGames() {
		LOG.info("Getting all users.");
		return gameRepository.findAll();
	}

	@GetMapping("/find-by-id/{gameId}")
	public Game findGame(@PathVariable("gameId") String gameId) {

		Game game = this.gameRepository.findById(gameId).get();
		return game;

	}

	@PostMapping(value = "/create")
	public Game addNewGame(@RequestBody Game game) {
		LOG.info("Saving game.");

		return gameRepository.save(game);
	}

	@GetMapping("/winning-combinations")
	public List<WinningCombination> getWinningCombinations(){
		return this.slotService.getWinningCombinations();
	}

	@PostMapping("/add-turn/{gameId}")
	public boolean addGameTurn(@PathVariable("gameId") String gameId, @RequestBody Turn turn) {
		Game game = gameRepository.findById(gameId).get();

//		String symbol1 =this.slotService.generateRandomSymbol();
//		String symbol2 = this.slotService.generateRandomSymbol();
//		String symbol3 = this.slotService.generateRandomSymbol();
//
//		turn.setSymbol1(symbol1);
//		turn.setSymbol2(symbol2);
//		turn.setSymbol3(symbol3);
		game.getTurns().add(turn);

		List<String> symbolsAll = Stream.of(turn.getSymbol1(), turn.getSymbol2(), turn.getSymbol3()).collect(Collectors.toList());

		List<WinningCombination> winningCombinations = this.slotService.getWinningCombinations();
		System.out.println("WINNING COMBINATIONS: " + winningCombinations);
		WinningCombination winningCombinationWhichApplies = null;
		for(WinningCombination winningCombination : winningCombinations) {
			if(winningCombination.applies(symbolsAll)) {
				winningCombinationWhichApplies = winningCombination;
				break;
			}
		}

		System.out.println("WINNING COMBINATION WHICH APPLIES: " + winningCombinationWhichApplies);

		boolean won = false;
		if(winningCombinationWhichApplies != null) {
			// player won, increase buget by amount bet * ratio
			won = true;
			game.setCurrentBuget(game.getCurrentBuget() + turn.getAmountBet() * winningCombinationWhichApplies.getRatio());
		}else {
			// player lost
			game.setCurrentBuget(game.getCurrentBuget() - turn.getAmountBet());
		}

		Game savedGame = this.gameRepository.save(game);
		TurnDto turnDto = new TurnDto();
		turnDto.setTurn(turn);
		turnDto.setWinningCombination(winningCombinationWhichApplies);
		return won;
	}


}