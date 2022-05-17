package ro.unibuc.slots.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    private long id;
    private String content;

    @JsonCreator
    public Greeting(@JsonProperty("id") final long id, @JsonProperty("content") final String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
