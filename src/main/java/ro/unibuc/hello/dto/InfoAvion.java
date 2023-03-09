package ro.unibuc.hello.dto;

public class InfoAvion {

    private long id;
    private String content;

    public InfoAvion() {
    }

    public InfoAvion(long id, String content) {
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