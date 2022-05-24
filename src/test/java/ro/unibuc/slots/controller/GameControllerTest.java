package ro.unibuc.slots.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.slots.model.Game;
import ro.unibuc.slots.service.GameService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class GameControllerTest {
	@Mock
	private GameService gameService;

	@InjectMocks
	private GameController gameController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void test_addNewGame() throws Exception {
		final Game game = new Game();

		when(gameService.addNewGame(any(Game.class))).thenReturn(game);

		final MvcResult result = mockMvc.perform(post("/rest/game/create")
				.content(objectMapper.writeValueAsString(game))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(game));
	}
}
