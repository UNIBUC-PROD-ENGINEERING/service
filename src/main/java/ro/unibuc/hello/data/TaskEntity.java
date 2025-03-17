package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class TaskEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private int points;
    private boolean completed;
    private String partyId;
    private String assignedUserId;

    public TaskEntity() {}

    public TaskEntity(String name, String description, int points, String partyId, String assignedUserId) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.completed = false;
        this.partyId = partyId;
        this.assignedUserId = assignedUserId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getPartyId() { return partyId; }
    public void setPartyId(String partyId) { this.partyId = partyId; }

    public String getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(String assignedUserId) { this.assignedUserId = assignedUserId; }
}
