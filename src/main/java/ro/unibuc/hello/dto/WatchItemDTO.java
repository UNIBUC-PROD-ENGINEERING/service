package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;
import ro.unibuc.hello.data.WatchItemEntity;

import java.util.Objects;

public class WatchItemDTO {
    @Id
    private String id;
    private MovieDTO movie;
    private UserDTO user;

    public WatchItemDTO(WatchItemEntity watchItem) {
        this.id = watchItem.getId();
        this.movie = new MovieDTO(watchItem.getCompositeKey().getMovie());
        this.user = new UserDTO(watchItem.getCompositeKey().getUser());
    }

    public WatchItemDTO() {
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public void setMovie(MovieDTO movie) {
        this.movie = movie;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchItemDTO that = (WatchItemDTO) o;
        return id.equals(that.id) && movie.equals(that.movie) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, user);
    }

    @Override
    public String toString() {
        return "WatchItemDTO{" +
                "id='" + id + '\'' +
                ", movie=" + movie +
                ", user=" + user +
                '}';
    }
}
