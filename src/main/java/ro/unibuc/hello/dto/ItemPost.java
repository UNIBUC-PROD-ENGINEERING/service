package ro.unibuc.hello.dto;

public class ItemPost {
    private String sessionId;
    private String name;
    private String description;
    
    public ItemPost() {}

    public ItemPost(String sessionId, String name, String description) {
        this.sessionId = sessionId;
        this.name = name;
        this.description = description;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
