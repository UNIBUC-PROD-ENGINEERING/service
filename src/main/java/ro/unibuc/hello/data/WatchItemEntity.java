package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document
public class WatchItemEntity {
    @Id
    private String id;

    private CompositeKey compositeKey;

    public WatchItemEntity(CompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompositeKey getCompositeKey() {
        return compositeKey;
    }

    public void setCompositeKey(CompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchItemEntity watchItem = (WatchItemEntity) o;
        return id.equals(watchItem.id) && compositeKey.equals(watchItem.compositeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compositeKey);
    }

    @Override
    public String toString() {
        return "WatchItemEntity{" +
                "id='" + id + '\'' +
                ", compositeKey=" + compositeKey +
                '}';
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKey that = (CompositeKey) o;
            return movie.equals(that.movie) && user.equals(that.user);
        }

        @Override
        public int hashCode() {
            return Objects.hash(movie, user);
        }

        @Override
        public String toString() {
            return "CompositeKey{" +
                    "movie=" + movie +
                    ", user=" + user +
                    '}';
        }
    }
}
