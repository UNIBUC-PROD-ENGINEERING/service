package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "parties")
public class PartyEntity {
    @Id
    private String id;
    private String name;
    private String date;
    private String locationId;  // Reference to a Location
    private List<String> foodIds;  // References to Food items
    private List<String> userIds;  // Users in the party
    private List<String> playlistIds = new ArrayList<>();  // Songs in the playlist
    private List<String> taskIds;  // Tasks to complete
    private int partyPoints;  // Sum of all users' points

    public PartyEntity() {}

    public PartyEntity(String name, String date) {
        this.name = name;
        this.date = date;
        this.locationId = "";
        this.foodIds = new ArrayList<>();
        this.userIds = new ArrayList<>();
        this.playlistIds = new ArrayList<>();
        this.taskIds = new ArrayList<>();
        this.partyPoints = 0;

    }

  
    public PartyEntity(String name, String date, String userId) {
    this.name = name;
    this.date = date;
    this.partyPoints = 0;
    this.userIds = new ArrayList<>();
    this.userIds.add(userId);
}


    // Getters and Setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<String> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<String> foodIds) {
        this.foodIds = foodIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void addFood(String foodId) {
        this.foodIds.add(foodId);
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(List<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void addTask(String taskId) {
        if (this.taskIds == null) {
            this.taskIds = new ArrayList<>();
        }
        this.taskIds.add(taskId);
    }

    public int getPartyPoints() {
        return partyPoints;
    }

    public void setPartyPoints(int partyPoints) {
        this.partyPoints = partyPoints;
    }

    public void addSong(String songId) {
        this.playlistIds.add(songId);
    }
    
    public void removeSong(String songId) {
        this.playlistIds.remove(songId);
    }
    
}
