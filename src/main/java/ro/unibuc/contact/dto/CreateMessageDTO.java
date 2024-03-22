package ro.unibuc.contact.dto;
import javax.validation.constraints.NotBlank;

public class CreateMessageDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Subject is mandatory")
    private String subject;

    @NotBlank(message = "Body is mandatory")
    private String body;

    public CreateMessageDTO() {

    }
    public CreateMessageDTO(String username, String subject, String body) {
        this.username = username;
        this.subject = subject;
        this.body = body;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
