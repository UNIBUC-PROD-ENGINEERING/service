package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private PlayerRepository playerRepository;
	ObjectMapper objectMapper = new ObjectMapper();
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		try{

		playerRepository.deleteAll();
		
		JsonNode rootNode = objectMapper.readTree(new File("Player.json"));
		JsonNode playersNode = rootNode.get("players");
		for (JsonNode playerNode : playersNode){
			playerRepository.save(new PlayerEntity(playerNode.get("name").asText(),
			playerNode.get("team").asText(),
			playerNode.get("points_per_game").asDouble(),
			playerNode.get("rebounds_per_game").asDouble(),
			playerNode.get("assists_per_game").asDouble()
		));
		}
		
		
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}


}
