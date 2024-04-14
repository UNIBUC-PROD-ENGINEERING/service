package ro.unibuc.hello.dto;

public class Greeting {

    private String id;
    private String content;

    public Greeting() {
    }

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
