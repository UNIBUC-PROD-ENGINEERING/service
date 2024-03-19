package ro.unibuc.triplea.infrastructure.games.steam.gateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.triplea.domain.games.steam.gateway.SteamGameGateway;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;

@Component
@Primary
@RequiredArgsConstructor
@Slf4j
public class SteamGameGatewayImpl implements SteamGameGateway {

    @Value("${STEAM_API_KEY}")
    private String steamApiKey;
    
    private final RestTemplate restTemplate;

    @Override
    public List<SteamGame> getSteamGames(Optional<Integer> count) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsonNode responseNode = responseEntity.getBody();

            List<SteamGame> games = new ArrayList<>();
            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");

                    int limit = count.orElse(Integer.MAX_VALUE);
                    int counter = 0;
                    for (JsonNode app : apps) {
                        if (counter >= limit) {
                            break;
                        }

                        if (app.get("name") != null && !app.get("name").asText().isEmpty()) {
                            SteamGame game = SteamGame.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            games.add(game);

                            counter++;
                        }
                    }
                }
            }

            return games;
        } else {
            log.error("Failed to fetch games from the external API. Status code: {}", responseEntity.getStatusCode());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<SteamGame> getSteamGameBySteamId(int gameSteamId) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsonNode responseNode = responseEntity.getBody();

            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");
                    for (JsonNode app : apps) {
                        if (app.get("appid").asInt() == gameSteamId) {
                            SteamGame game = SteamGame.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            return Optional.of(game);
                        }
                    }
                }
            }
        } else {
            log.error("Failed to fetch game from the external API. Status code: {}", responseEntity.getStatusCode());
        }

        return Optional.empty();
    }

    @Override
    public Optional<SteamGame> getSteamGameByName(String gameName) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsonNode responseNode = responseEntity.getBody();

            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");
                    for (JsonNode app : apps) {
                        if (app.get("name").asText().equalsIgnoreCase(gameName)) {
                            SteamGame game = SteamGame.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            return Optional.of(game);
                        }
                    }
                }
            }
        } else {
            log.error("Failed to fetch game from the external API. Status code: {}", responseEntity.getStatusCode());
        }
        
        return Optional.empty();
    }
    
}
