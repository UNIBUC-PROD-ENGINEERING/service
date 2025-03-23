package ro.unibuc.hello.dto;

public class Session {
    private String sessionId;
    private UserDetails user;

    public Session() {}

    public Session(String sessionId, UserDetails user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}
