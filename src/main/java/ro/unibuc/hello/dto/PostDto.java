package ro.unibuc.hello.dto;

import java.time.LocalDateTime;
import java.util.Objects; 

public class PostDto {
    private String title;
    private String location;
    private Integer totalNumberOfPlayers;

    public PostDto(String title, String location, Integer totalNumberOfPlayers) {
        this.title = title;
        this.location = location;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    public PostDto() {}

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Integer getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalNumberOfPlayers(Integer totalNumberOfPlayers) {
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PostDto other = (PostDto) obj;
        return Objects.equals(title, other.title) &&
                Objects.equals(location, other.location) &&
                Objects.equals(totalNumberOfPlayers, other.totalNumberOfPlayers);
    }

}