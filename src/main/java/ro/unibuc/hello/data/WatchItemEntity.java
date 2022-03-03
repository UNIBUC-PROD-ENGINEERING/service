package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class WatchItemEntity {
    @Id
    private CompositeKey id;

    public WatchItemEntity(CompositeKey id) {
        this.id = id;
    }

    public WatchItemEntity() {
    }

    public CompositeKey getId() {
        return id;
    }

    public void setId(CompositeKey id) {
        this.id = id;
    }

    public static class CompositeKey implements Serializable {
        @DBRef()
        private MovieEntity movie;
        @DBRef()
        private UserEntity user;

        public CompositeKey(MovieEntity movie, UserEntity user) {
            this.movie = movie;
            this.user = user;
        }

        public CompositeKey() {
        }

        public MovieEntity getMovie() {
            return movie;
        }

        public void setMovie(MovieEntity movie) {
            this.movie = movie;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }
    }
}
