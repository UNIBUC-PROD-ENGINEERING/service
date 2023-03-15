package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("movie")
public class MovieEntity {
    @Id
    private String id;
    private String title;
    private String year;
    private String description;
    private String type;

    public MovieEntity() {
    }

    public MovieEntity(String title, String year, String description, String type) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.type = type;
    }

    public void setId(String id) {
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

    public String getId() {
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


    @Override
    public String toString() {
        return String.format(
                "Movie[id='%s', title='%s', year='%s', description='%s', type='%s']",
                id, title, year, description, type);
    }

}
