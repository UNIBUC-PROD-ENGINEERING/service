package ro.unibuc.hello.dto;

public class LogoutRequest {
    private String sessionId;

    public LogoutRequest() {}

    public LogoutRequest(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
