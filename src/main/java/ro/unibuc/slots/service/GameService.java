package ro.unibuc.slots.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.unibuc.slots.model.Game;
import ro.unibuc.slots.model.Turn;
import ro.unibuc.slots.model.WinningCombination;
import ro.unibuc.slots.repo.GameRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GameService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final GameRepository gameRepository;

	private final List<WinningCombination> winningCombinations = Stream.of(
			new WinningCombination(1.0, "*", "7", "_"),
			new WinningCombination(5.0, "_", "_", "7"),
			new WinningCombination(10.0, "7", "7", "7"),
			new WinningCombination(10.0, "+", "+", "+"),
			new WinningCombination(10.0, "_", "_", "_"),
			new WinningCombination(10.0, "/", "/", "/")
	).collect(Collectors.toList());

	public GameService(final GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public List<Game> getAllGames() {
		logger.info("Getting all users...");
		return gameRepository.findAll();
	}

	public Game findGame(final String id) {
		return gameRepository.findById(id).orElse(null);
	}

	public Game addNewGame(final Game game) {
		logger.info("Saving game...");
		return gameRepository.save(game);
	}

	public List<WinningCombination> getWinningCombinations() {
		return winningCombinations;
	}

	public boolean addGameTurn(final String id, final Turn turn) {
		final Game game = findGame(id);
		game.getTurns().add(turn);

		List<String> symbolsAll = Stream.of(turn.getSymbol1(), turn.getSymbol2(), turn.getSymbol3()).collect(Collectors.toList());

		System.out.println("WINNING COMBINATIONS: " + winningCombinations);
		WinningCombination winningCombinationWhichApplies = null;
		for (WinningCombination winningCombination : winningCombinations) {
			if (winningCombination.applies(symbolsAll)) {
				winningCombinationWhichApplies = winningCombination;
				break;
			}
		}

		System.out.println("WINNING COMBINATION WHICH APPLIES: " + winningCombinationWhichApplies);

		boolean won = false;
		if (winningCombinationWhichApplies != null) {
			// player won, increase buget by amount bet * ratio
			won = true;
			game.setCurrentBuget(game.getCurrentBuget() + turn.getAmountBet() * winningCombinationWhichApplies.getRatio());
		} else {
			// player lost
			game.setCurrentBuget(game.getCurrentBuget() - turn.getAmountBet());
		}

		addNewGame(game);
		return won;
	}
}
