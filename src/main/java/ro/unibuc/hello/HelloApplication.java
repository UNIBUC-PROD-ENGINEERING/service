package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.data.TeamRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private GameRepository gameRepository;

	ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		try {
			//Adding players to mongoDB
			playerRepository.deleteAll();
			JsonNode rootNode = objectMapper.readTree(new File("Player.json"));
			JsonNode playersNode = rootNode.get("players");
			for (JsonNode playerNode : playersNode) {
				playerRepository.save(new PlayerEntity(
					playerNode.get("id").asInt(),
					playerNode.get("name").asText(),
						playerNode.get("team").asText(),
						playerNode.get("points_per_game").asDouble(),
						playerNode.get("rebounds_per_game").asDouble(),
						playerNode.get("assists_per_game").asDouble()));
			}
			//Adding teams to mongoDB
			teamRepository.deleteAll();
			rootNode = objectMapper.readTree(new File("Team.json"));
			JsonNode teamsNode = rootNode.get("teams");
			for (JsonNode teamNode : teamsNode) {
				String id=teamNode.get("id").asText();
				String name = teamNode.get("name").asText();
				int yearFounded = teamNode.get("year_founded").asInt();
				String coach = teamNode.get("coach").asText();
				JsonNode playersJson = teamNode.get("players");
				List<Integer> players = new ArrayList<>();
				for (JsonNode playerJson : playersJson) {
					players.add(playerJson.asInt());
				}
				
				TeamEntity teamEntity = new TeamEntity(id,name, players, yearFounded, coach);
				teamRepository.save(teamEntity);
			}
			gameRepository.deleteAll();
			rootNode=objectMapper.readTree(new File("Game.json"));
			JsonNode gamesNode=rootNode.get("games");
			for(JsonNode gameNode:gamesNode){
				String id=gameNode.get("id").asText();
				String date=gameNode.get("date").asText();
				int team1_id=gameNode.get("team1_id").asInt();
				int team2_id=gameNode.get("team2_id").asInt();
				String score=gameNode.get("score").asText();
				int spectators=gameNode.get("spectators").asInt();
				GameEntity gameEntity=new GameEntity(id,date, team1_id, team2_id, score, spectators);
				System.out.println(gameEntity);

				gameRepository.save(gameEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
