package ro.unibuc.slots.model;

import org.springframework.data.annotation.Id;

public class InformationEntity {
    @Id
    public String id;

    public String title;
    public String description;

    public InformationEntity(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Information[id='%s', title='%s', description='%s']",
                id, title, description
        );
    }
}
