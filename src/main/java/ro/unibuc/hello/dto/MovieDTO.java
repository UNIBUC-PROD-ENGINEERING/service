package ro.unibuc.hello.dto;
public class MovieDTO {
    private long id;
    private String title;
    private String year;
    private String description;
    private String type;

    public MovieDTO() {
    }

    public MovieDTO(long id, String title, String year, String description, String type) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
        this.type = type;
    }
    public MovieDTO(String title, String year, String description, String type) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getYear() {
        return year;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
}
