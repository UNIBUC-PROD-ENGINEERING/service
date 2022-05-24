package com.slots.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.slots.app.model.Turn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.slots.app.controllers.GameController;
import com.slots.app.model.Game;
import com.slots.app.repo.GameRepository;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class AppApplicationTests {

	@Test
	void contextLoads() {
	}

	@MockBean
	private GameRepository repoGame;

	@Autowired
	private ApplicationContext applicationContext;

	private void setup() {

		Game game1 = new Game();
		Game game2 = new Game();
		List<Game> gamesDinDb = Stream.of(game1, game2).collect(Collectors.toCollection(ArrayList::new));
		when(repoGame.findAll()).thenReturn(gamesDinDb);
		when(repoGame.findById(anyString())).thenReturn(Optional.of(game1));
	}



	@Test
	public void test_getAllGames() {
		this.setup();
		System.out.println("HELLO");
		GameController controller = applicationContext.getBean(GameController.class);
		assertNotNull(controller.getAllGames());

	}

	@Test
	public void test_testWinningCombinationTurn() {
		this.setup();
		System.out.println("HELLO");
		GameController controllerUser = applicationContext.getBean(GameController.class);
		Turn turn = new Turn();
		turn.setSymbol1("+");
		turn.setSymbol2("+");
		turn.setSymbol3("+");
		boolean result = controllerUser.addGameTurn("", turn);
		assertTrue(result);

	}

}
