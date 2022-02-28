package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class ReviewEntity {
    @Id
    private String id;
    private String comment;
    private Integer score;
    @DBRef(lazy = true)
    private MovieEntity movie;

    public ReviewEntity(String comment, Integer score) {
        this.comment = comment;
        this.score = score;
    }

    public ReviewEntity(String comment, Integer score, MovieEntity movie) {
        this.comment = comment;
        this.score = score;
        this.movie = movie;
    }

    public ReviewEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return id.equals(that.id) && Objects.equals(comment, that.comment) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, score);
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", score=" + score +
                '}';
    }
}
