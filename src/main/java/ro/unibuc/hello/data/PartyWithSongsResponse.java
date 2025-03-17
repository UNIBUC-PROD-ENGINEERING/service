package ro.unibuc.hello.data;

import java.util.List;

public class PartyWithSongsResponse {
    private String id;
    private String name;
    private String date;
    private String locationName;
    private List<String> foodNames;
    private List<String> userNames;
    private List<String> songNames;
    private List<String> taskDescriptions;
    private int partyPoints;

    public PartyWithSongsResponse(String id, String name, String date, String locationName, List<String> foodNames,
                                  List<String> userNames, List<String> songNames, List<String> taskDescriptions, int partyPoints) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.locationName = locationName;
        this.foodNames = foodNames;
        this.userNames = userNames;
        this.songNames = songNames;
        this.taskDescriptions = taskDescriptions;
        this.partyPoints = partyPoints;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(List<String> foodNames) {
        this.foodNames = foodNames;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<String> getSongNames() {
        return songNames;
    }

    public void setSongNames(List<String> songNames) {
        this.songNames = songNames;
    }

    public List<String> getTaskDescriptions() {
        return taskDescriptions;
    }

    public void setTaskDescriptions(List<String> taskDescriptions) {
        this.taskDescriptions = taskDescriptions;
    }

    public int getPartyPoints() {
        return partyPoints;
    }

    public void setPartyPoints(int partyPoints) {
        this.partyPoints = partyPoints;
    }
}
