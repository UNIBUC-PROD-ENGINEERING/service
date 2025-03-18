package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "games")
public class Game {

    @Id
    private String id;

    @NotBlank(message = "Game name is required")
    private String name;

    @NotBlank(message = "Platform is required")
    private String platform;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Release year is required")
    private Integer releasedYear;

    // Default constructor
    public Game() {
    }

    // Constructor with all fields except id
    public Game(String name, String platform, String genre, Integer releasedYear) {
        this.name = name;
        this.platform = platform;
        this.genre = genre;
        this.releasedYear = releasedYear;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(Integer releasedYear) {
        this.releasedYear = releasedYear;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", genre='" + genre + '\'' +
                ", releasedYear=" + releasedYear +
                '}';
    }
}