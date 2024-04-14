package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class InformationEntity {

    @Id
    private String id;

    private String title;
    private String description;

    public InformationEntity() {}

    public InformationEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public InformationEntity(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id='%s', title='%s', description='%s']",
                id, title, description);
    }
}
