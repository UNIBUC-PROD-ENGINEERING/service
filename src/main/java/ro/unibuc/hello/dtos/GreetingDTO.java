package ro.unibuc.hello.dtos;

public class GreetingDTO {

    private long id;
    private String content;

    public GreetingDTO() {
    }

    public GreetingDTO(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}