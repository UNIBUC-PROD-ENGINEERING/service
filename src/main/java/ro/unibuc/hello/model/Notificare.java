package ro.unibuc.hello.model;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notificari")
public class Notificare {
    @Id
    private String notificareId;
    @Indexed
    private String eventId;
    @Indexed
    private String userId;
    private String tipVerificare;
    private boolean verificare;

    public Notificare() {
    }
    
    public Notificare(String notificareId, String eventId, String userId, String tipVerificare, boolean verificare) {
        this.notificareId = notificareId;
        this.eventId = eventId;
        this.userId = userId;
        this.tipVerificare = tipVerificare;
        this.verificare = verificare;
    }

    public void setNotificareId(String notificareId) {
        this.notificareId = notificareId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTipVerificare(String tipVerificare) {
        this.tipVerificare = tipVerificare;
    }

    public void setVerificare(boolean verificare) {
        this.verificare = verificare;
    }

    public String getNotificareId() {
        return notificareId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTipVerificare() {
        return tipVerificare;
    }

    public boolean getVerificare() {
        return verificare;
    }


}
