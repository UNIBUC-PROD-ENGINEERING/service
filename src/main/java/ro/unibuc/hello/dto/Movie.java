package ro.unibuc.hello.dto;

public class Movie {
    public String id;

    public String title;

    public String description;

    public Integer runtime;

    public Movie() {

    }

    public Movie(String id, String title, String description, Integer runtime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.runtime = runtime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String content) {
        this.title = content;
    }
    public void setDescription(String description){ this.description = description; }
    public void setRuntime(Integer runtime){ this.runtime = runtime; }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription(){ return description; }
    public Integer getRuntime(){ return runtime; }

}
