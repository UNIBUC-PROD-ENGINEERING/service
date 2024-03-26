package ro.unibuc.triplea.infrastructure.games.steam.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SteamGameGatewayImplTest {

    @Value("${STEAM_API_KEY}")
    private String steamApiKey;

    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private final SteamGameGatewayImpl steamGameGateway = new SteamGameGatewayImpl(restTemplate);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetSteamGames_SuccessfulResponse() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        List<SteamGame> expectedGames = createMockSteamGames();
        List<SteamGame> actualGames = steamGameGateway.getSteamGames(Optional.empty());

        assertEquals(expectedGames, actualGames);
    }

    @Test
    public void testGetSteamGames_SuccessfulResponse_Limit() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        List<SteamGame> expectedGames = createMockSteamGames().subList(0, 2);
        List<SteamGame> actualGames = steamGameGateway.getSteamGames(Optional.of(2));

        assertEquals(expectedGames, actualGames);
    }

    @Test
    public void testGetSteamGames_SuccessfulResponse_Empty() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        List<SteamGame> actualGames = steamGameGateway.getSteamGames(Optional.empty());

        assertEquals(Collections.emptyList(), actualGames);
    }

    @Test
    public void testGetSteamGames_FailedResponse() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        List<SteamGame> actualGames = steamGameGateway.getSteamGames(Optional.empty());

        assertEquals(Collections.emptyList(), actualGames);
    }

    @Test
    public void testGetSteamGameBySteamId_ExistingGame() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> expectedGame = Optional
                .of(SteamGame.builder().gameSteamId(730).gameName("Counter-Strike: Global Offensive").build());
        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameBySteamId(730);

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void testGetSteamGameBySteamId_NonExistingGame() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameBySteamId(9999);

        assertFalse(actualGame.isPresent());
    }

    @Test
    public void testGetSteamGameBySteamId_NonExistingGame_FailedResponse() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameBySteamId(9999);

        assertFalse(actualGame.isPresent());
    }

    @Test
    public void testGetSteamGameByName_ExistingGame() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> expectedGame = Optional
                .of(SteamGame.builder().gameSteamId(730).gameName("Counter-Strike: Global Offensive").build());
        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameByName("Counter-Strike: Global Offensive");

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void testGetSteamGameByName_NonExistingGame() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(createMockResponseNode(), HttpStatus.OK);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameByName("Non-existing Game");

        assertFalse(actualGame.isPresent());
    }

    @Test
    public void testGetSteamGameByName_NonExistingGame_FailedResponse() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(eq(apiUrl), eq(JsonNode.class))).thenReturn(responseEntity);

        Optional<SteamGame> actualGame = steamGameGateway.getSteamGameByName("Non-existing Game");

        assertFalse(actualGame.isPresent());
    }

    private JsonNode createMockResponseNode() {
        List<SteamGame> steamGames = createMockSteamGames();

        ArrayNode appsArrayNode = objectMapper.createArrayNode();
        for (SteamGame steamGame : steamGames) {
            ObjectNode appNode = objectMapper.createObjectNode();
            appNode.put("appid", steamGame.getGameSteamId());
            appNode.put("name", steamGame.getGameName());
            appsArrayNode.add(appNode);
        }

        ObjectNode applistNode = objectMapper.createObjectNode();
        applistNode.set("apps", appsArrayNode);

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.set("applist", applistNode);

        return rootNode;
    }

    private List<SteamGame> createMockSteamGames() {
        List<SteamGame> steamGames = new ArrayList<>();
        steamGames.add(SteamGame.builder().gameSteamId(730).gameName("Counter-Strike: Global Offensive").build());
        steamGames.add(SteamGame.builder().gameSteamId(570).gameName("Dota 2").build());
        steamGames.add(SteamGame.builder().gameSteamId(440).gameName("Team Fortress 2").build());
        steamGames.add(SteamGame.builder().gameSteamId(578080).gameName("PLAYERUNKNOWN'S BATTLEGROUNDS").build());

        return steamGames;
    }

}
