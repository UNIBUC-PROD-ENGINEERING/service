package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ArtworkEntity {

    @Id
    public String id;

    public String title;
    public String artist;
    public String description;
    public String image;

    public ArtworkEntity() {}

    public ArtworkEntity(String title, String artist, String description, String image) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[title='%s', description='%s', image='%s']",
                id, title, description, image);
    }

}