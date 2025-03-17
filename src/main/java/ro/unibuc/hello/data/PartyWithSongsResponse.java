package ro.unibuc.hello.data;

import java.util.List;

public class PartyWithSongsResponse {
    private String id;
    private String name;
    private String date;
    private String locationId;
    private List<String> foodIds;
    private List<String> userIds;
    private List<String> songNames;  // To hold song names
    private List<String> taskIds;
    private int partyPoints;

    public PartyWithSongsResponse(String id, String name, String date, String locationId, List<String> foodId,
                                  List<String> userIds, List<String> songNames, List<String> taskIds, int partyPoints) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.locationId = locationId;
        this.foodIds = foodId;
        this.userIds = userIds;
        this.songNames = songNames;
        this.taskIds = taskIds;
        this.partyPoints = partyPoints;
    }

    // Getters and setters

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

    public void addFood(String foodId) {
        this.foodIds.add(foodId);
    }


    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getSongNames() {
        return songNames;
    }

    public void setSongNames(List<String> songNames) {
        this.songNames = songNames;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public int getPartyPoints() {
        return partyPoints;
    }

    public void setPartyPoints(int partyPoints) {
        this.partyPoints = partyPoints;
    }
}
