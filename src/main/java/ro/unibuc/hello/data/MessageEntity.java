package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class MessageEntity{

    @Id
    public String id;

    public String subject;
    public String body;
    public String userId;

    public MessageEntity() {}

    public MessageEntity(String subject, String body, String userId ) {
        this.subject = subject;
        this.body = body;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format(
                "Message[subject='%s', body='%s', userId='%s']",
                id, subject, body, userId);
    }

}