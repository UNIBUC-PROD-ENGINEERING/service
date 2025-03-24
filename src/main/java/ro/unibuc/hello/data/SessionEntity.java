package ro.unibuc.hello.data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

@Document
public class SessionEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String sessionId;

    @DocumentReference
    private UserEntity user;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expiresAt;

    public SessionEntity() {}

    public SessionEntity(String sessionId, UserEntity user, LocalDateTime expiresAt) {
        this.sessionId = sessionId;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public SessionEntity(String id, String sessionId, UserEntity user, LocalDateTime expiresAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
