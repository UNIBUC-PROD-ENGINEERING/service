package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class ArtworkEntity {

    @Id
    @Indexed(unique = true)
    public String id;

    public String title;
    public String artist;
    public String description;
    public String image;

    public ArtworkEntity(String id,String title, String artist, String description, String image) {
        this.id=id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[title='%s', description='%s', image='%s']",
                id, title, description, image);
    }

}