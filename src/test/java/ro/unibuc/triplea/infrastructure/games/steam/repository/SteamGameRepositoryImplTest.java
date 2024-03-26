package ro.unibuc.triplea.infrastructure.games.steam.repository;

import org.junit.jupiter.api.Test;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.gateway.SteamGameGateway;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SteamGameRepositoryImplTest {

    private final SpringDataGameRepository springDataGameRepository = mock(SpringDataGameRepository.class);

    private final SteamGameGateway steamGameGateway = mock(SteamGameGateway.class);

    private SteamGameRepositoryImpl steamGameRepository = new SteamGameRepositoryImpl(springDataGameRepository,
            steamGameGateway);

    @Test
    public void testFindGames() {
        int count = 2;
        List<SteamGame> steamGames = new ArrayList<>();
        steamGames.add(SteamGame.builder().gameSteamId(1).gameName("Game 1").build());
        steamGames.add(SteamGame.builder().gameSteamId(2).gameName("Game 2").build());

        List<SteamGameResponse> expectedResponses = new ArrayList<>();
        expectedResponses.add(SteamGameResponse.builder().gameSteamId(1).gameName("Game 1").build());
        expectedResponses.add(SteamGameResponse.builder().gameSteamId(2).gameName("Game 2").build());

        when(steamGameGateway.getSteamGames(Optional.of(count))).thenReturn(steamGames);

        List<SteamGameResponse> responses = steamGameRepository.findGames(Optional.of(count));

        assertEquals(count, responses.size());
        assertEquals(expectedResponses, responses);
    }

    @Test
    public void testSave() {
        SteamGame game = SteamGame.builder().gameSteamId(1).gameName("Game 1").build();
        when(springDataGameRepository.save(game)).thenReturn(game);

        SteamGame savedGame = steamGameRepository.save(game);

        assertEquals(game, savedGame);
    }

    @Test
    public void testFindByGameSteamId() {
        int gameSteamId = 123;
        SteamGame game = SteamGame.builder().gameSteamId(gameSteamId).gameName("Game 1").build();
        Optional<SteamGameResponse> expectedResponse = Optional.of(SteamGameResponse.builder().gameSteamId(gameSteamId)
                .gameName("Game 1").build());

        when(steamGameGateway.getSteamGameBySteamId(gameSteamId)).thenReturn(Optional.of(game));

        Optional<SteamGameResponse> response = steamGameRepository.findByGameSteamId(gameSteamId);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testFindByGameSteamId_NotFound() {
        int gameSteamId = 123;
        Optional<SteamGame> game = Optional.empty();

        when(steamGameGateway.getSteamGameBySteamId(gameSteamId)).thenReturn(game);

        Optional<SteamGameResponse> response = steamGameRepository.findByGameSteamId(gameSteamId);

        assertEquals(Optional.empty(), response);
    }

    @Test
    public void testFindByGameName() {
        String gameName = "Game 1";
        SteamGame game = SteamGame.builder().gameSteamId(1).gameName(gameName).build();
        Optional<SteamGameResponse> expectedResponse = Optional.of(SteamGameResponse.builder().gameSteamId(1)
                .gameName(gameName).build());

        when(steamGameGateway.getSteamGameByName(gameName)).thenReturn(Optional.of(game));

        Optional<SteamGameResponse> response = steamGameRepository.findByGameName(gameName);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testFindByGameName_NotFound() {
        String gameName = "Game 1";
        Optional<SteamGame> game = Optional.empty();

        when(steamGameGateway.getSteamGameByName(gameName)).thenReturn(game);

        Optional<SteamGameResponse> response = steamGameRepository.findByGameName(gameName);

        assertEquals(Optional.empty(), response);
    }

}