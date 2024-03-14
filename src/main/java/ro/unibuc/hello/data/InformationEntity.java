package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class InformationEntity {
    @Id
    public String id;
    public String title;
    public String description;

    public InformationEntity() {}

    public InformationEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id=%s, title='%s', description='%s']",
                id, title, description);
    }
}