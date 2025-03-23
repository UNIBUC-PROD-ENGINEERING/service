package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.SessionEntity;

public class Session {
    private String sessionId;
    private User user;

    public Session() {}

    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public Session(SessionEntity entity) {
        this(entity.getId(), new User(entity.getUser()));
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
