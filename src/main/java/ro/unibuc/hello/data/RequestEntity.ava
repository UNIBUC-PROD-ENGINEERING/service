package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class RequestEntity {

    @Id
    private String id;

    private String text;

    public RequestEntity() {}

    public RequestEntity(String text) {
        this.text = text;
    }

    public RequestEntity(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id='%s', text='%s']",
                id, text);
    }
}
