package ro.unibuc.hello.dto;

import java.util.Date;

public class Notification {
    private String id;
    private String message;
    private Date timestamp;

    public Notification() {}

    public Notification(String id, String message, Date timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}