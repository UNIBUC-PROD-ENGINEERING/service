package ro.unibuc.slots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Game {
	@Id
	private String id;

	private Date creationDate = new Date();
	//	private Map<String, String> userSettings = new HashMap<>();
	private List<Turn> turns = new ArrayList<>();

	private Double currentBuget;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Turn> getTurns() {
		return turns;
	}

	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}

	public Double getCurrentBuget() {
		return currentBuget;
	}

	public void setCurrentBuget(Double currentBuget) {
		this.currentBuget = currentBuget;
	}


}
